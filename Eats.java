// RestaurantMatchingApp.java

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class RestaurantMatchingApp extends Application {
    public static final String NOTIFICATION_CHANNEL_ID = "restaurant_matches";
    
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Restaurant Matches",
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for restaurant matches and group invites");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

// Models

public class User {
    private String userId;
    private String name;
    private String email;
    private List<String> foodPreferences;
    private int searchRadius; // in kilometers
    private List<String> blockedRestaurants;
    private List<Group> groups;
    private boolean hasCompletedOnboarding;
    private String profileImageUrl;
    private Map<String, Boolean> dietaryRestrictions;
    
    // Getters and setters
    public boolean hasCompletedOnboarding() {
        return hasCompletedOnboarding;
    }
    
    public void setHasCompletedOnboarding(boolean hasCompletedOnboarding) {
        this.hasCompletedOnboarding = hasCompletedOnboarding;
    }
}

public class Group {
    private String groupId;
    private List<User> members;
    private List<PendingInvite> pendingInvites;
    private Map<String, List<RestaurantVote>> userVotes;
    private static final long INVITE_TIMEOUT_MS = 24 * 60 * 60 * 1000; // 24 hours
    
    public void inviteUser(User user) {
        PendingInvite invite = new PendingInvite(user, System.currentTimeMillis());
        pendingInvites.add(invite);
        sendInviteNotification(user);
    }
    
    public void handleInviteResponse(User user, boolean accepted) {
        if (accepted) {
            members.add(user);
        }
        pendingInvites.removeIf(invite -> invite.getUser().equals(user));
        updateGroupStatus();
    }
}

public class Restaurant {
    private String id;
    private String name;
    private String cuisine;
    private Location location;
    private double rating;
    private String imageUrl;
}

public class RestaurantVote {
    private String userId;
    private String restaurantId;
    private boolean liked;
    private long timestamp;
}

public class PendingInvite {
    private User user;
    private long timestamp;
    
    public boolean hasTimedOut() {
        return System.currentTimeMillis() - timestamp > Group.INVITE_TIMEOUT_MS;
    }
}

// Services

public class RestaurantMatchingService {
    private final RestaurantRepository repository;
    
    public List<Restaurant> getMatchingRestaurants(Group group) {
        Set<String> commonPreferences = findCommonPreferences(group);
        int minRadius = findMinRadius(group);
        
        return repository.findRestaurants(commonPreferences, minRadius)
            .stream()
            .filter(restaurant -> !isBlockedByAnyMember(restaurant, group))
            .collect(Collectors.toList());
    }
    
    public void handleVote(User user, Restaurant restaurant, boolean liked) {
        RestaurantVote vote = new RestaurantVote(user.getUserId(), restaurant.getId(), liked);
        saveVote(vote);
        checkForMatch(restaurant, user.getGroups());
    }
    
    private void checkForMatch(Restaurant restaurant, List<Group> groups) {
        for (Group group : groups) {
            if (isRestaurantMatch(restaurant, group)) {
                notifyGroupMatch(group, restaurant);
            }
        }
    }
}

// UI Components

public class RestaurantSwipeActivity extends AppCompatActivity {
    private RestaurantMatchingService matchingService;
    private CardStackView cardStackView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_swipe);
        
        cardStackView = findViewById(R.id.card_stack_view);
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);
        cardStackView.setLayoutManager(layoutManager);
        
        loadRestaurants();
    }
    
    private void handleSwipe(Direction direction) {
        boolean liked = direction == Direction.Right;
        Restaurant restaurant = getCurrentRestaurant();
        matchingService.handleVote(getCurrentUser(), restaurant, liked);
    }
}

public class GroupInviteActivity extends AppCompatActivity {
    private GroupService groupService;
    
    private void updateInviteStatus() {
        List<PendingInvite> pendingInvites = getCurrentGroup().getPendingInvites();
        if (pendingInvites.isEmpty()) {
            showCompletedStatus();
        } else {
            showWaitingStatus(pendingInvites);
        }
    }
}

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailInput, passwordInput;
    private Button loginButton, registerButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        
        auth = FirebaseAuth.getInstance();
        
        // Check if user is already logged in
        if (auth.getCurrentUser() != null) {
            navigateToOnboarding();
        }
        
        initializeViews();
        setupClickListeners();
    }
    
    private void initializeViews() {
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
    }
    
    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegistration());
    }
    
    private void navigateToOnboarding() {
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }
}

public class OnboardingActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Button nextButton;
    private static final int TOTAL_PAGES = 3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        
        viewPager = findViewById(R.id.onboarding_pager);
        nextButton = findViewById(R.id.next_button);
        
        viewPager.setAdapter(new OnboardingPagerAdapter());
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtonText(position);
            }
        });
    }
    
    private void updateButtonText(int position) {
        if (position == TOTAL_PAGES - 1) {
            nextButton.setText("Get Started");
            nextButton.setOnClickListener(v -> navigateToProfileSetup());
        }
    }
    
    private void navigateToProfileSetup() {
        startActivity(new Intent(this, ProfileSetupActivity.class));
        finish();
    }
}

public class ProfileSetupActivity extends AppCompatActivity {
    private EditText nameInput;
    private MultiSelectSpinner foodPreferencesSpinner;
    private SeekBar radiusSeekBar;
    private Button finishButton;
    private UserService userService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        
        initializeViews();
        setupListeners();
    }
    
    private void saveProfile() {
        User user = new User();
        user.setName(nameInput.getText().toString());
        user.setFoodPreferences(foodPreferencesSpinner.getSelectedItems());
        user.setSearchRadius(radiusSeekBar.getProgress());
        
        userService.saveUser(user, success -> {
            if (success) {
                navigateToMainActivity();
            }
        });
    }
}

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_swipe:
                    showFragment(new RestaurantSwipeFragment());
                    return true;
                case R.id.nav_groups:
                    showFragment(new GroupsFragment());
                    return true;
                case R.id.nav_profile:
                    showFragment(new ProfileFragment());
                    return true;
            }
            return false;
        });
    }
}

public class GroupCreationActivity extends AppCompatActivity {
    private EditText groupNameInput;
    private RecyclerView friendsList;
    private Button createGroupButton;
    private GroupService groupService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        
        initializeViews();
        setupFriendsList();
        
        createGroupButton.setOnClickListener(v -> createGroup());
    }
    
    private void createGroup() {
        String groupName = groupNameInput.getText().toString();
        List<User> selectedFriends = getSelectedFriends();
        
        Group group = new Group();
        group.setName(groupName);
        group.setMembers(selectedFriends);
        
        groupService.createGroup(group, success -> {
            if (success) {
                finish();
            }
        });
    }
}

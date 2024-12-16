// Firebase configuration
const firebaseConfig = {
    // Will add configuration from Firebase Console
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

class AppDemo {
    constructor() {
        this.currentView = 'auth';
        this.views = {
            auth: this.createAuthView(),
            onboarding: this.createOnboardingView(),
            profileSetup: this.createProfileSetupView(),
            main: this.createMainView()
        };
        
        this.init();
    }

    init() {
        this.showView(this.currentView);
        this.setupAuth();
    }

    createAuthView() {
        const view = document.createElement('div');
        view.className = 'view auth-view';
        view.innerHTML = `
            <div class="auth-container">
                <img src="assets/logo.png" alt="Eats Logo" class="logo">
                <input type="email" placeholder="Email" id="email-input">
                <input type="password" placeholder="Password" id="password-input">
                <button id="login-btn">Login</button>
                <button id="register-btn">Register</button>
            </div>
        `;
        return view;
    }

    createOnboardingView() {
        const view = document.createElement('div');
        view.className = 'view onboarding-view';
        // Add onboarding content
        return view;
    }

    createProfileSetupView() {
        const view = document.createElement('div');
        view.className = 'view profile-setup-view';
        // Add profile setup content
        return view;
    }

    createMainView() {
        const view = document.createElement('div');
        view.className = 'view main-view';
        // Add main content
        return view;
    }

    showView(viewName) {
        Object.values(this.views).forEach(v => 
            v.style.transform = 'translateX(100%)'
        );
        this.views[viewName].style.transform = 'translateX(0)';
        this.currentView = viewName;
    }

    setupAuth() {
        firebase.auth().onAuthStateChanged(user => {
            if (user) {
                this.showView('main');
            } else {
                this.showView('auth');
            }
        });
    }

    handleError(error) {
        console.error('App Error:', error);
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error';
        errorDiv.textContent = 'An error occurred. Please try again.';
        this.screen.appendChild(errorDiv);
        setTimeout(() => errorDiv.remove(), 3000);
    }

    showLoading(show = true) {
        const loader = document.getElementById('loading');
        loader.classList.toggle('hidden', !show);
    }
}

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new AppDemo();
}); 
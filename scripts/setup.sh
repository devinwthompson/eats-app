#!/bin/bash

echo "Setting up Eats Android Project..."

# Check for required tools
command -v java >/dev/null 2>&1 || { echo "Java not found. Please install JDK 8 or higher"; exit 1; }
command -v gradle >/dev/null 2>&1 || { echo "Gradle not found. Please install Android Studio first"; exit 1; }

# Create project directory structure
mkdir -p app/src/main/java/com/example/eats
mkdir -p app/src/main/res/{layout,values,drawable,menu}
mkdir -p docs

# Download required dependencies
echo "Downloading dependencies..."
./gradlew build

# Firebase setup prompt
echo "
Firebase Setup Required:
1. Go to console.firebase.google.com
2. Create new project 'Eats'
3. Add Android app with package 'com.example.eats'
4. Download google-services.json
5. Place it in the app/ directory
"

# Check for google-services.json
if [ ! -f "app/google-services.json" ]; then
    echo "Warning: google-services.json not found!"
    echo "Please complete Firebase setup"
fi

echo "
To publish to Play Store, you'll need:
1. Google Play Developer account ($25 one-time fee)
2. Signed APK/Bundle
3. Privacy Policy
4. Store listing assets
" 

# Run tests
echo "Running automated tests..."
./gradlew test
./gradlew connectedAndroidTest

# Generate debug APK
echo "Building debug APK..."
./gradlew assembleDebug

# Generate release bundle
echo "Preparing release bundle..."
if [ -f "keystore.properties" ]; then
    ./gradlew bundleRelease
else
    echo "Warning: keystore.properties not found. Cannot generate release bundle."
fi

# Check code quality
echo "Running lint checks..."
./gradlew lint

# Generate documentation
echo "Generating documentation..."
./gradlew dokka
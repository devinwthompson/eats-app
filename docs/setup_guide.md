# Eats App Setup Guide

## Development Environment Setup
1. Install Android Studio
2. Install Android SDK (minimum API 24)
3. Install JDK 8 or higher

## Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project named "Eats"
3. Add Android app with package name "com.example.eats"
4. Download google-services.json and place in app/
5. Enable Authentication with Email/Password sign-in
6. Set up Firestore Database with default security rules

## Project Structure
- `app/src/main/java/com/example/eats/` - Java source files
- `app/src/main/res/` - Resources (layouts, strings, etc.)
- `app/src/main/res/layout/` - UI layouts
- `app/src/main/res/values/` - Resource values
- `app/src/main/res/drawable/` - Images and icons

## Required Icons
Create these vector drawables in `app/src/main/res/drawable/`:
- ic_swipe.xml
- ic_groups.xml
- ic_profile.xml
- app_logo.xml 
# Publishing to Google Play Store

## Prerequisites
1. Google Play Developer Account
   - Sign up at play.google.com/console
   - Pay one-time $25 registration fee
   - Complete account details

2. App Signing   ```bash
   # Generate keystore
   keytool -genkey -v -keystore eats.keystore -alias eats -keyalg RSA -keysize 2048 -validity 10000   ```

3. Required Assets
   - High-res icon (512x512 PNG)
   - Feature graphic (1024x500 PNG)
   - Phone screenshots (min 2)
   - Short description (80 chars)
   - Full description (4000 chars)
   - Privacy policy URL

## Publishing Steps
1. Create Release
   - Open Play Console
   - Create new application
   - Go to Production track

2. App Bundle   ```bash
   # Generate signed bundle
   ./gradlew bundleRelease   ```

3. Store Listing
   - Fill app details
   - Upload graphics
   - Set content rating
   - Price & distribution

4. Submit for Review
   - Review time: 2-7 days
   - Address any policy violations
   - Respond to reviewer comments

## Requirements Checklist
- [ ] Developer account
- [ ] App signing key
- [ ] Privacy policy
- [ ] Content rating
- [ ] Graphics assets
- [ ] App description
- [ ] Working contact email
- [ ] Compliant permissions 
# API Endpoints Documentation

## Authentication
- POST /auth/register
- POST /auth/login
- POST /auth/logout

## User Management
- GET /user/{userId}
- PUT /user/{userId}
- GET /user/{userId}/preferences
- PUT /user/{userId}/preferences

## Groups
- POST /groups
- GET /groups/{groupId}
- PUT /groups/{groupId}
- POST /groups/{groupId}/invite
- POST /groups/{groupId}/vote

## Restaurants
- GET /restaurants
- GET /restaurants/{restaurantId}
- POST /restaurants/{restaurantId}/vote
- GET /restaurants/matches/{groupId}

## Notes
- All endpoints require authentication token
- Rate limiting applies to all endpoints
- Responses are in JSON format 
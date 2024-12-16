package com.example.eats.data

object DatabaseValidator {
    fun validateUser(user: Map<String, Any>): Boolean {
        return user.run {
            containsKey("userId") &&
            containsKey("name") &&
            containsKey("email") &&
            get("foodPreferences") is List<*> &&
            get("searchRadius") is Number &&
            get("blockedRestaurants") is List<*>
        }
    }

    fun validateGroup(group: Map<String, Any>): Boolean {
        return group.run {
            containsKey("groupId") &&
            containsKey("name") &&
            get("members") is List<*> &&
            get("pendingInvites") is List<*> &&
            get("userVotes") is Map<*, *>
        }
    }

    fun validateRestaurant(restaurant: Map<String, Any>): Boolean {
        return restaurant.run {
            containsKey("id") &&
            containsKey("name") &&
            containsKey("cuisine") &&
            get("location") is Map<*, *> &&
            get("rating") is Number
        }
    }
} 
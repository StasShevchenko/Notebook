package com.example.notebook.feature_notebook.presentation.util

import android.os.Bundle
import androidx.navigation.NavType
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.google.gson.Gson

class OrganizationInfoNavType : NavType<OrganizationInfo>(
    isNullableAllowed = false
){
    override fun get(bundle: Bundle, key: String): OrganizationInfo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): OrganizationInfo {
        return Gson().fromJson(value, OrganizationInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: OrganizationInfo) {
        bundle.putParcelable(key, value)
    }
}
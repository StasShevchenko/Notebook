package com.example.notebook.feature_notebook.domain.model

import android.os.Parcelable
import com.example.notebook.feature_notebook.domain.model.entities.Organization
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganizationInfo(
    val organizationId: Int,
    val organizationName: String,
    val workersAmount: Int,
    val organizationType: String,
    val typeId: Int
): Parcelable


fun OrganizationInfo?.toOrganization(): Organization?{
    if (this != null) {
        return Organization(
            this.organizationId,
            this.organizationName,
            this.workersAmount,
            this.typeId
        )
    }
    else {
        return null
    }
}

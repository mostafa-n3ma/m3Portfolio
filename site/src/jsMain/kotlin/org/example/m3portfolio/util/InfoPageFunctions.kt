package org.example.m3portfolio.util

import org.example.m3portfolio.Ids
import org.example.m3portfolio.models.Info


fun calculateInfoPageValues(): Info {
    return Info(
        name =getElementValue(Ids.info_NameField,InputType.InputField),
        imageUrl = getElementValue(Ids.info_ImageUrlField,InputType.InputField),
        role =getElementValue(Ids.info_RoleField,InputType.InputField),
        address =getElementValue(Ids.info_AddressField,InputType.InputField),
        phone =getElementValue(Ids.info_PhoneField,InputType.InputField),
        email =getElementValue(Ids.info_EmailField,InputType.InputField),
        linkedIn =getElementValue(Ids.info_LinkedInField,InputType.InputField),
        github =getElementValue(Ids.info_GithubField,InputType.InputField),
        bio =getElementValue(Ids.info_BiotextArea,InputType.TextArea),//
        education =getElementValue(Ids.info_EducationField,InputType.InputField),
        skills =getElementValue(Ids.info_SkillsField,InputType.InputField),
        resumeLink =getElementValue(Ids.info_ResumeLinkField,InputType.InputField),
        extra =getElementValue(Ids.info_ExtraField,InputType.InputField),
    )
}


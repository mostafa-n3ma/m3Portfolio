package org.example.m3portfolio.util

import org.example.m3portfolio.Ids
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Gallery
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Website


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
        bio =getElementValue(Ids.info_Bio_editor,InputType.TextArea),//
        education =getElementValue(Ids.info_EducationField,InputType.InputField),
        skills =getElementValue(Ids.info_SkillsField,InputType.InputField),
        programLanguages =getElementValue(Ids.info_programmingLAnguagesField,InputType.InputField),
        tools =getElementValue(Ids.info_toolsField,InputType.InputField),
        frameWorks =getElementValue(Ids.info_framworksField,InputType.InputField),
        resumeLink =getElementValue(Ids.info_ResumeLinkField,InputType.InputField),
        extra =getElementValue(Ids.info_ExtraField,InputType.InputField),
    )
}

fun calculateExperiencePageValues():Experience{
    return Experience(
        description = getElementValue(Ids.exp_description_Editor,InputType.TextArea),
        duration = getElementValue(Ids.exp_duration_Field,InputType.InputField),
        image = getElementValue(Ids.exp_image_Field,InputType.InputField),
        location = getElementValue(Ids.exp_location_Field,InputType.InputField),
        projects = getElementValue(Ids.exp_projects_Field,InputType.InputField),
        role =getElementValue(Ids.exp_role_Field,InputType.InputField),
    )
}

fun calculatingProjectPageValues():Project{
    return Project(
        title = getElementValue(Ids.project_title_field,InputType.InputField),
        subTitle = getElementValue(Ids.project_sub_title_field,InputType.InputField),
        description = getElementValue(Ids.project_description_editor,InputType.TextArea),
        techStack = getElementValue(Ids.project_techStack_field,InputType.InputField),
        repoLink = getElementValue(Ids.project_repoLink_field,InputType.InputField),
        videoLink = getElementValue(Ids.project_videoLink_field,InputType.InputField),
        mainImageLink = getElementValue(Ids.project_mainImageLink_field,InputType.InputField),
        date =getElementValue(Ids.project_date_field,InputType.InputField),
    )
}


fun calculatingCertificatePageValues():Certificate{
    return Certificate(
        title = getElementValue(Ids.certificate_title_field,InputType.InputField),
        from = getElementValue(Ids.certificate_from_field,InputType.InputField),
        link = getElementValue(Ids.certificate_link_field,InputType.InputField),
        date = getElementValue(Ids.certificate_date_field,InputType.InputField),
    )
}


fun calculatingWebsitePageValues():Website{
    return Website(
        title = getElementValue(Ids.website_title_field,InputType.InputField),
        icon = getElementValue(Ids.website_icon_field,InputType.InputField),
        link = getElementValue(Ids.website_link_field,InputType.InputField),
    )
}



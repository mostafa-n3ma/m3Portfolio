package org.example.m3portfolio

object Constants{
    const val DATABASE_NAME = "portfolio_database"

    const val REMEMBERED_STORAGE_VALUE = "remember"
    const val USER_ID_STORAGE_VALUE = "userId"
    const val USER_NAME_STORAGE_VALUE = "username"

    const val FONT_FAMILY = "Roboto"

    const val EXPERIENCE_ID_PARAM = "expId"
    const val PROJECT_ID_PARAM = "projectid"

}

object Measurements{
    const val SIDE_PANEL_WIDTH = 150
    const val PAGE_WIDTH = 1920
    const val HEADER_HEIGHT = 100
    const val COLLAPSED_PANEL_HEIGHT = 50
}








object ApiPaths{
    //info
    const val READ_INFO_PATH = "readinfo"
    const val UPDATE_INFO_PATH = "updateinfo"

    //experience
    const val READ_EXPERIENCE_PATH = "readexperince"
    const val READ_EXPERIENCE_BY_ID_PATH = "readexperincebyid"
    const val UPDATE_EXPERIENCE_PATH = "updateexperince"
    const val ADD_EXPERIENCE_PATH = "addexperince"



    // project
    const val READ_PROJECTS_PATH = "readprojects"
    const val READ_PROJECTS_BY_ID_PATH = "readprojectsbyid"
    const val UPDATE_PROJECTS_PATH = "updateprojects"
    const val ADD_PROJECTS_PATH =    "addprojects"
    const val DELETE_PROJECTS_PATH =    "deleteprojects"



    //certificates
    const val READ_CERTIFICATES_PATH = "readcertificates"


    //websites
    const val READ_WEBSITE_PATH = "readwebsites"



    // user check
    const val USER_CHECK_PATH = "usercheck"
    const val USER_CHECK_ID_PATH = "useridcheck"



}






object Res{
    object Image{
        const val my_image = "/mostafa_n3ma.svg"
    }

    object Icon{
        const val bold = "/bold.svg"
        const val italic = "/italic.svg"
        const val link = "/link.svg"
        const val title = "/title.svg"
        const val subtitle = "/subTitle.svg"
        const val image = "/image.svg"
        const val checkmark = "/checkmark.svg"
    }
}




object Ids{

    //control popup
    const val linkHrefInput = "linkHrefInput"
    const val linkTitleInput = "linkTitleInput"



    //login screen
    const val login_userNameInput = " usernameInput"
    const val login_passweordInput = " passwordinput"

    // Info Screen
    const val info_NameField = "info_NameField"
    const val info_ImageUrlField = "info_ImageUrlField"
    const val info_RoleField = "info_RoleField"
    const val info_AddressField = "info_AddressField"
    const val info_PhoneField = "info_PhoneField"
    const val info_EmailField = "info_EmailField"
    const val info_LinkedInField = "info_LinkedInField"
    const val info_GithubField = "info_GithubField"
    const val info_Bio_editor = "info_BioField"
    const val info_Bio_preview = "info_BioFieldDiv"
    const val info_EducationField = "info_EducationField"
    const val info_SkillsField = "info_SkillsField"
    const val info_ResumeLinkField = "info_ResumeLinkField"
    const val info_ExtraField = "info_ExtraField"



    //Experience Screen
    const val exp_description_Editor = "exp_description_Editor"
    const val exp_description_Preview = "exp_description_Preview"
    const val exp_duration_Field = "exp_duration_Field"
    const val exp_image_Field = "exp_image_Field"
    const val exp_location_Field = "exp_location_Field"
    const val exp_projects_Field = "exp_projects_Field"
    const val exp_role_Field = "exp_role_Field"


    const val project_title_field = "project_title_field"
    const val project_description_editor = "project_description_editor"
    const val project_description_preview = "project_description_preview"
    const val project_techStack_field = "project_techStack_field"
    const val project_repoLink_field = "project_repoLink_field"
    const val project_videoLink_field = "project_videoLink_field"
    const val project_mainImageLink_field = "project_mainImageLink_field"



    const val project_date_field = "project_date_field"


}
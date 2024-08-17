package org.example.m3portfolio

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.example.m3portfolio.models.Visitor

object Constants{
    const val DATABASE_NAME = "portfolio_database"

    const val REMEMBERED_STORAGE_VALUE = "remember"
    const val USER_ID_STORAGE_VALUE = "userId"
    const val USER_NAME_STORAGE_VALUE = "username"

    const val FONT_FAMILY = "RobotoRegular"
    const val ROBOTO_CONDENSED = "RobotoCondensedBold"
    const val ROBOTO_REGULAR = "RobotoRegular"

    const val EXPERIENCE_ID_PARAM = "expId"
    const val PROJECT_ID_PARAM = "projectid"
    const val CERTIFICATE_ID_PARAM = "certificateid"
    const val WEBSITE_ID_PARAM = "websiteid"
    const val VISITOR_ID_PARAM = "visitorId"
    const val GALLERY_ID_PARAM = "galleryid"

    enum class Languages( name:String){
        EN("English"),
        AR("Arabic")
    }
    const val LANGUAGE_STORAGE_VALUE = "display_language"
    const val COLOR_MODE = "colorMode"
    const val VISITOR_ID = "visitorId"


    const val MAIN_SECTION = "MAIN_section"
    const val PROJECTS_SECTION = "projects_section"
    const val CERTIFICATES_SECTION = "CERTIFICATES_section"
    const val SKILLS_SECTION = "SKILLS_SECTION"
    const val EXPERIENCE_SECTION = "EXPERIENCE_SECTION"



    const val PROJECT_MAIN_SECTION = "PROJECT_MAIN_SECTION"
    const val PROJECT_DESCRIPTION_SECTION = "PROJECT_DESCRIPTION_SECTION"

    enum class HeaderNavigationItemsTypes{
        IndexHeaders,
        ProjectPreviewHeaders
    }




    const val LANGUAGES_SPLITTER_CODE = "@AR@"

}

object Measurements{
    const val SIDE_PANEL_WIDTH = 150
    const val PAGE_WIDTH = 1920
    const val HEADER_HEIGHT = 54
    const val COLLAPSED_PANEL_HEIGHT = 50
}




data class Strings(val EN: String, val AR: String)

object AppStrings {
    //header
    val MostafaN3ma = Strings("Mostafa N3ma", "مصطفى نعمه")
    val headerHomeText = Strings("Home", "الرئيسية")
    val headerProjectsText = Strings("Projects", "المشاريع")
    val headerCertificatesText = Strings("Certificates", "الشهادات")
    val headerSkillsText = Strings("Skills", "المهارات")
    val headerExperienceText = Strings("Expertise", "الخبرات")


    val projectMainHeader = Strings("Main","الرئيسية")
    val projectDescriptionHeader = Strings("Description","الوصف")
    val projectTechStack = Strings("TechStack","التقنيات المستخدمة")


    //projects section
    val projects_i_worked_on = Strings("Projects I worked on","مشاريع عملت عليها")
    val see_more = Strings("See More","اظهار المزيد")


    // certificates section
    val certificates_i_hold = Strings("Certificates I hold","شهادات احملها")

    //skills section
    val set_of_skills_i_have = Strings("Set Of Skills I Have:","مجموعة مهارات اتقنها")
    val programming_languages = Strings("Programming Languages :","اللغات البرمجية")
    val tools = Strings("Tools :","الادوات")
    val frameworks = Strings("FrameWorks : :","أطر العمل")


    // experiences section
    val experiences =Strings("Experience :","الخبرات")

    //footer
    val copyRights = Strings("Mostafa N3ma (V 1.0) - Copyright @ 2024 Personal Website","مصطفى نعمة (الإصدار 1.0) - حقوق الطبع والنشر @ 2024 الموقع الشخصي")


}


fun getLangString(string:Strings,lang:Constants.Languages):String{
   return when(lang){
        Constants.Languages.EN -> string.EN
        Constants.Languages.AR -> string.AR
       else-> string.EN
    }
}





object ApiPaths{
    //info
    const val READ_INFO_PATH = "readinfo"
    const val UPDATE_INFO_PATH = "updateinfo"


    const val COUNT_VISITORS = "count_visitors"
    const val READ_VISITORS = "read_visitors"
    const val READ_VISITOR_BY_ID = "read_visitor_by_id"
    const val UPDATE_VISITOR = "update_visitor"
    const val RECORD_VISITOR = "record_visitor"

    //experience
    const val READ_EXPERIENCE_PATH = "readexperince"
    const val READ_EXPERIENCE_BY_ID_PATH = "readexperincebyid"
    const val UPDATE_EXPERIENCE_PATH = "updateexperince"
    const val ADD_EXPERIENCE_PATH = "addexperince"
    const val DELETE_EXPERIENCES_PATH = "deleteexperiences"



    // project
    const val READ_PROJECTS_PATH = "readprojects"
    const val READ_PROJECTS_BY_ID_PATH = "readprojectsbyid"
    const val UPDATE_PROJECTS_PATH = "updateprojects"
    const val ADD_PROJECTS_PATH =    "addprojects"
    const val DELETE_PROJECTS_PATH =    "deleteprojects"



    //certificates
    const val READ_CERTIFICATES_PATH = "readcertificates"
    const val READ_CERTIFICATES_BY_ID_PATH = "readcertificatesbyid"
    const val UPDATE_CERTIFICATES_PATH = "updatecertificates"
    const val ADD_CERTIFICATES_PATH = "addcertificates"
    const val DELETE_CERTIFICATES_PATH = "deletecertificates"


    //websites
    const val READ_WEBSITE_PATH = "readwebsites"
    const val READ_WEBSITE_BY_ID_PATH = "readwebsitesbyid"
    const val UPDATE_WEBSITE_PATH = "upadatewebsites"
    const val ADD_WEBSITE_PATH = "addwebsites"
    const val DELETE_WEBSITES_PATH = "deleteebsites"


    //gallery
    const val  READ_GALLERY_PATH = "readgalleryimgs"
    const val  READ_GALLERY_BY_ID_PATH = "readgallerybyid"
    const val  ADD_GALLERY_PATH = "addgallery"
    const val  DELETE_GALLERIES_PATH = "deletegalleries"







    // user check
    const val USER_CHECK_PATH = "usercheck"
    const val USER_CHECK_ID_PATH = "useridcheck"



}






object Res{
    object Image{
        const val my_image_jpg = "/me_j.jpg"
        const val my_image_svg = "/me_vector.svg"
        const val certificateIcon = "/certificateIco.png"
        const val language = "/language.png"
        const val android_ic = "/android_ic.png"
        const val android_studio_ic = "/android_studio_ic.png"
        const val compos_ic = "/compos_ic.png"
        const val java_ic = "/java_ic.png"
        const val kotlin_ic = "/kotlin_ic.png"
        const val Github = "/Github.png"


        const val c_plus = "/c_plus.png"
        const val figma = "/figma.png"

        const val intellij = "/intellij.png"
        const val mongo_compass = "/mongo_compass.png"

        const val postmen = "/postmen.png"
        const val python = "/python.png"

        const val sql = "/sql.png"
        const val vs_code = "/vs_code.png"






    }

    object Icon{
        const val bold = "/bold.svg"
        const val italic = "/italic.svg"
        const val link = "/link.svg"
        const val title = "/title.svg"
        const val subtitle = "/subTitle.svg"
        const val image = "/image.svg"
        const val checkmark = "/checkmark.svg"
        const val sun = "/sun.png"
        const val moon = "/moon.png"
    }
}




object Ids{


    //control popup
    const val linkHrefInput = "linkHrefInput"
    const val linkTitleInput = "linkTitleInput"


//Admin
    //login screen
    const val login_userNameInput = " usernameInput"
    const val login_passweordInput = " passwordinput"

    //Admin Info Screen
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
    const val info_programmingLAnguagesField = "info_programmingLAnguagesField"
    const val info_toolsField = "info_toolsField"
    const val info_framworksField = "info_framworksField"
    const val info_ResumeLinkField = "info_ResumeLinkField"
    const val info_ExtraField = "info_ExtraField"



    //Admin Experience Screen
    const val exp_description_Editor = "exp_description_Editor"
    const val exp_description_Preview = "exp_description_Preview"
    const val exp_duration_Field = "exp_duration_Field"
    const val exp_image_Field = "exp_image_Field"
    const val exp_location_Field = "exp_location_Field"
    const val exp_projects_Field = "exp_projects_Field"
    const val exp_role_Field = "exp_role_Field"

//Admin Project Screen
    const val project_title_field = "project_title_field"
    const val project_sub_title_field = "project_sub_title_field"
    const val project_description_editor = "project_description_editor"
    const val project_description_preview = "project_description_preview"
    const val project_techStack_field = "project_techStack_field"
    const val project_repoLink_field = "project_repoLink_field"
    const val project_videoLink_field = "project_videoLink_field"
    const val project_mainImageLink_field = "project_mainImageLink_field"
    const val project_date_field = "project_date_field"



    //Admin Certificates Screen
    const val certificate_title_field = "certificate_title_field"
    const val certificate_from_field = "certificate_from_field"
    const val certificate_link_field = "certificate_link_field"
    const val certificate_date_field = "certificate_date_field"
    const val certificate_thumbnailInput_field = "certificate_thumbnailInput_field"



    //Admin Websites Screen
    const val website_title_field = "website_title_field"
    const val website_link_field = "website_link_field"
    const val website_icon_field = "website_icon_field"


    // Admin Gallery
    const val gallery_category_field = "gallery_category_field"
    const val gallery_base64_field = "gallery_base64_field"





    //Main Home Index Screen
    const val mainSectionBioDiv = "mainSectionBioDiv"
    const val projectPreviewDescriptionDiv = "projectPreviewDescriptionDiv"


}


fun getCurrentDateTime(): Instant {
    return Clock.System.now()
}
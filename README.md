## :briefcase: M3Portfolio

#### :globe_with_meridians: A Multiplatform Personal Portfolio with Dynamic Content Management, Multi-Language Support, and Automated Deployment

**M3Portfolio** is a comprehensive personal portfolio website developed using **Kobweb**, a framework built on top of Kotlin HTML. Designed as a multiplatform application, M3Portfolio serves both as a dynamic showcase for projects, certificates, skills, and detailed personal information, and as an administrative tool for managing website content.

This project features a server-side application connected to **MongoDB**, enabling secure and efficient data handling through RESTful API endpoints. It supports **user authentication**, allowing for differentiated access between regular users and admin. The static website component, written in Kotlin and running in the browser, interacts seamlessly with the backend to provide a responsive and interactive user experience.

**M3Portfolio** is hosted on **Render**, utilizing **Docker** for automated deployment. This setup allows for streamlined updates and management directly from the project's GitHub repository, ensuring that the latest changes are easily reflected in the live version.

### :star: Key Features:

- :lock: **User Authentication:** Secure login for both regular users and admin, with password encryption using SHA-256.
- :floppy_disk: **MongoDB Integration:** Robust database connectivity for storing and retrieving user data, project information, and content management.
- :arrows_clockwise: **API Endpoints:** Efficient data exchange between server and client using GET and POST calls.
- :memo: **Serialization/Deserialization:** Classes are serialized for data exchange and deserialized back for processing.
- :camera_flash: **Image Upload:** Supports image uploads, encoded as base64, ensuring compatibility and security.
- :iphone: **Responsive Design:** Adaptable layouts for various screen sizes (XL, LG, MD, SM), ensuring optimal viewing across devices.
- :earth_americas: **Language Support:** Multi-language support with a dropdown menu for switching between English and Arabic.
- :bulb: **Dark/Light Mode:** Offers both dark and light color schemes for user preference.
- :compass: **Navigation Panels:** Includes main and specific navigation panels to enhance user experience.
- :hammer: **Admin Dashboard:** Comprehensive admin control panel for adding, editing, and deleting content.
- :chart_with_upwards_trend: **Visitor Analytics:** Tracks new visitors and session history, providing insights into user engagement.
- :page_facing_up: **Live Resume Preview:** Direct integration with Google Drive for live resume viewing and downloading.
- :link: **Social Links:** Quick navigation to GitHub, LinkedIn, and other social profiles.
- :rocket: **Automated Deployment:** Utilizes Docker for containerization and Render for hosting, enabling automated deployment from GitHub.

**M3Portfolio** employs a structured approach using an MVVM-like architecture:

- **commonMain:** Houses shared models, utility functions, and navigation constants.
- **jsMain:** Contains all the UI components, styles, and client-side logic.
- **jvmMain:** Manages server-side functionality, including MongoDB interactions and API endpoints.

With animations, transitions, and efficient data handling, **M3Portfolio** not only provides a platform to showcase personal achievements but also empowers the user with control over their online presence. The deployment setup with Docker and Render ensures the project remains easily maintainable and scalable, making it a dynamic and versatile personal website solution.

---

### :wrench: Detailed Tech Stack

- :computer: **Kotlin:** The primary programming language used for the entire project, both on the client and server sides.
- :globe_with_meridians: **Kobweb:** A framework built on top of Kotlin for building modern web applications, supporting multiplatform capabilities.
- :art: **Jetpack Compose for Web:** Used for building the UI components of the website, leveraging the Compose framework’s capabilities.
- :file_cabinet: **MongoDB:** A NoSQL database for storing user data and project information, accessed via the MongoDB Kotlin driver.
- :left_right_arrow: **Serialization:** Kotlinx Serialization for converting objects to JSON format, facilitating server-client communication.
- :shield: **SHA-256:** For password hashing, providing security by encoding user passwords before database storage.
- :framed_picture: **Base64 Encoding:** Used for encoding images before uploading to the server, ensuring secure data transfer.
- :cloud: **Render:** A cloud platform for hosting, enabling easy deployment and management of the application.
- :whale: **Docker:** Containerizes the application for consistent deployment across environments, aiding in automated deployment.
- :sparkles: **Font Awesome:** For adding icons to the UI, enhancing visual appeal and usability.
- :iphone: **Bootstrap:** Included via CDN for styling and responsive design, ensuring the website adapts to various screen sizes.
- :date: **Kotlinx Datetime:** For straightforward and consistent handling of date and time operations.

### :gear: Development Environment

- :robot: **Android Studio:** The IDE used for writing, building, and managing the Kotlin-based project.

---

### :building_construction: Architecture

- :building_blocks: **MVVM-like Architecture:** Separates models from views to ensure clean, maintainable, and scalable code, using shared code (`commonMain`), client-side code (`jsMain`), and server-side code (`jvmMain`).

---

### :framed_picture: Images Gallery

#### 1. **Main Index Page**

![main index page 1](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/main_index_page1.png?raw=true)
![main image 2](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/main_index_page2.png?raw=true)

- Welcome Message & Personal Info: Displays a welcoming introduction and personal image.
- Showcase Sections: Highlights projects, certificates, skills, and experiences.
- Main Header Panel: Features navigation items for different sections (e.g., projects, certificates) along with a color mode switch button and a language switch button.
- Responsive Design: Adapts layout dimensions based on screen size to ensure an optimal viewing experience across devices.

#### 2. **AboutMe Page**

![about me page image](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/about_me_page.png?raw=true)

- About Me: Get to know more about me, including my background, interests, and professional journey.
- Contact Information: For inquiries or collaborations, you can find my contact details here. Feel free to reach out!
- Responsive UI: The design adjusts to different screen sizes, ensuring a seamless and engaging experience on any device.

#### 3. **Project Preview Page**

![project preview image](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/project_preview.png?raw=true)

- Project Details: Showcases specific project details, descriptions, and GitHub repository links.
- Responsive UI: Layout adjusts to fit various screen sizes, maintaining usability and readability on different devices.

#### 4. **Admin Panel Page**

![admin page 1 login](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/admin_page1.png?raw=true)
![admin page dashboard](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/admin_page2.png?raw=true)

- Login Screen: Secure login screen to access the admin panel. Requires authentication for accessing the content management features.
- Dashboard Functionality: Provides a central control panel for managing project content.
- Navigation: Features a side sheet with links to manage projects, certificates, experiences, websites, image gallery, and main info.

![admin page 4](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/admin_page4.png?raw=true)
![admin page 3](https://github.com/mostafa-n3ma/m3Portfolio/blob/main/site/src/jsMain/resources/public/description%20images/admin_page3.png?raw=true)

- Content Management: Includes tools for adding, editing, deleting content, and uploading images or thumbnails.
- Responsive Design: Adapts to different screen sizes to ensure accessibility and ease of use.

![small admin](https://raw.githubusercontent.com/mostafa-n3ma/m3Portfolio/0cdb74c52be8bb53db16d463b4b94bd5a3fa2ccc/site/src/jsMain/resources/public/description%20images/admin_sm1.png)
![small admin](https://raw.githubusercontent.com/mostafa-n3ma/m3Portfolio/0cdb74c52be8bb53db16d463b4b94bd5a3fa2ccc/site/src/jsMain/resources/public/description%20images/admin_sm2.png)
![small admin](https://raw.githubusercontent.com/mostafa-n3ma/m3Portfolio/0cdb74c52be8bb53db16d463b4b94bd5a3fa2ccc/site/src/jsMain/resources/public/description%20images/admin_sm3.png)

### :pencil: Figma Design

![Figma Design](https://github.com/mostafa-n3ma/my-gallery-repository/blob/main/portfoliio_figma_design.png?raw=true)

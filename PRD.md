# Product Requirements Document (PRD): Channapatna Namma Pride 🎨🧸

## 1. Project Overview
**Channapatna Namma Pride** is a premium Android application designed to preserve, promote, and verify the cultural heritage of Channapatna toys. Known as the "Toy Town of Karnataka," Channapatna's traditional lacquerware craft is a protected Geographical Indication (GI). This app serves as a digital bridge between master artisans and global consumers, ensuring authenticity and fostering community empowerment.

## 2. Vision & Objectives
*   **Heritage Preservation**: Document and showcase the 200-year-old craft of lacquerware.
*   **Authenticity Assurance**: Provide a secure method for users to verify GI-tagged products.
*   **Artisan Empowerment**: Direct visibility for artisans, including their stories, specialties, and workshop locations.
*   **Cultural Accessibility**: Offer a native experience through full Kannada localization.

## 3. Target Audience
*   **Cultural Tourists**: Travelers visiting Channapatna looking for authentic experiences and workshops.
*   **Collectors & Enthusiasts**: Individuals seeking genuine, high-quality handcrafted toys and home decor.
*   **Local Community**: Artisans and residents of Channapatna who want to showcase their pride and heritage.

## 4. Functional Requirements

### 4.1 Home Screen (The Showcase)
*   **Hero Section**: Visually stunning introduction to Channapatna toys.
*   **GI Tag Intro**: Educational snippet about the importance of Geographical Indication.
*   **Craft Journey**: A scrollable section detailing the step-by-step process of toy making (wood seasoning, turning, lacquering).
*   **Featured Artisans**: Quick access to prominent artisan profiles.
*   **New Arrivals**: A catalog highlight of the latest toys added to the collection.

### 4.2 Heritage Verification (GI Verify)
*   **Search Functionality**: Users can enter a unique tracking ID or GI Tag number.
*   **Verification Results**: Display authenticity status, the artisan who made it, and the date of manufacture.
*   **Backend Integration**: Real-time verification against a secure Firebase Firestore database.

### 4.3 Artisan Profiles (Meet the Maker)
*   **Rich Bios**: Detailed stories of the artisans, their years of experience, and their legacy.
*   **Specialties**: Categorization of crafts (e.g., Nesting Dolls, Contemporary Decor, Educational Toys).
*   **Contact Info**: Direct contact details (phone) to facilitate B2B or direct purchases.
*   **Gallery**: High-quality images of the artisan and their signature works.

### 4.4 Interactive Workshop Map
*   **Google Maps Integration**: A custom map interface showing pins for all authentic workshops.
*   **Navigation**: Integration with Google Maps for turn-by-turn directions to artisan clusters.
*   **Workshop Details**: Distance from the highway/town center and brief descriptions of what to expect during a visit.

### 4.5 Localization
*   **Bilingual Interface**: Seamless switching between English and Kannada.
*   **Culturally Relevant Copy**: Natural and respectful translations that resonate with the local dialect.

## 5. Technical Requirements
*   **Platform**: Android (Min SDK 24, Target SDK 34).
*   **UI Framework**: Jetpack Compose (Material 3).
*   **Architecture**: MVVM (Model-View-ViewModel) for clean separation of concerns.
*   **Database**: Firebase Firestore (NoSQL) for scalable, real-time data.
*   **Media**: Coil for optimized image loading and caching.
*   **Maps**: Google Maps Compose SDK.

## 6. Design & Aesthetics
*   **Primary Palette**: 
    *   `Lacquer Red`: Representing the vibrant dyes.
    *   `Wood Brown`: Reflecting the sustainable Hale wood used.
    *   `Bone Surface`: For a premium, clean background.
*   **Typography**: Modern sans-serif (Inter/Outfit) paired with traditional-feeling accents.
*   **Visual Style**: Glassmorphism, subtle gradients, and high-quality photography to convey a "premium heritage" feel.

## 7. Data Model (Firestore Schema)

### Artisans
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | String | Unique Artisan ID |
| `name_en/kn` | String | Name in English/Kannada |
| `experience` | String | Years in the craft |
| `bio_en/kn` | String | Biographical details |
| `specialties` | Array | List of craft specialties |
| `imageUrl` | String | Profile image URL |

### Toys
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | String | Unique Product ID |
| `name_en/kn` | String | Product Name |
| `giTagNumber` | String | Official GI Tracking Number |
| `artisanId` | String | Reference to the maker |
| `imageUrl` | String | Product image URL |

### Workshops
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | String | Unique Workshop ID |
| `latitude/longitude` | Double | Geo-coordinates |
| `address_en/kn` | String | Physical address |

## 8. Success Metrics
*   **User Engagement**: Time spent on artisan profiles and the "Craft Journey" section.
*   **Verification Usage**: Number of successful toy authenticity checks.
*   **Artisan Connectivity**: Number of map navigations to workshop locations.

# Social Networking Application
A modern Spring Boot-based social networking platform with real-time messaging capabilities, comprehensive user management, and secure authentication.

# 🔐 Authentication & Security
* Secure User Sessions - Spring Security integration with JWT/session-based authentication
* Principal-based Authorization - User identity management via SecurityContext
* Protected Endpoints - All API routes require authentication
* Input Validation - Request data validation using Jakarta Validation
* Self-action Prevention - Users cannot perform restricted actions on themselves

# 👤 User Profile Management

* View Profile - Retrieve complete user profile information
* Update Profile - Modify name, bio (about), and phone number
* Profile Picture Upload - Upload and manage profile images with multipart file support
* Secure File Storage - Organized file storage with path management
* Profile Validation - Input sanitization and validation on all profile operations


# 🤝 Social Interaction
* User Search - Search users by username with partial matching
* Guest User Lookup - Find specific users by exact username
* User Blocking - Block unwanted users to prevent interactions
* Block Management - View and manage blocked users list
* Privacy Controls - Control who can interact with your profile

💬 Real-Time Chat (WebSocket)

* Private Messaging - One-to-one real-time communication
* Instant Delivery - Messages delivered instantly using WebSocket protocol
* STOMP Protocol - Efficient message brokering with STOMP
* SockJS Fallback - Browser compatibility with SockJS support

# 🛡️ Error Handling & Responses

* REST Principles - Clean and intuitive API endpoints
* HTTP Method Compliance - Proper use of GET, POST, PUT methods
* Request Validation - Automatic validation of request payloads
* Response DTOs - Structured data transfer objects for all responses

* kafka Integration - Real-time event streaming for enhanced performance and scalability
* Friend/follower system
# 🚀 Coming Soon

# new features and improvements are on the way! Stay tuned for updates on our roadmap, including enhanced social features, performance optimizations, and more robust security measures.



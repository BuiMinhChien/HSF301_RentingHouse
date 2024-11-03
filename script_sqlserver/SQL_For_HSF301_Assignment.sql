--------------------------------TAO DATABASE--------------------------------------------------


--create database project_house_rental_hsf301_assignment
use project_house_rental_hsf301_assignment

--------------------------------TAO BANG-----------------------------------------
-- 1. Các bảng không có khóa ngoại
CREATE TABLE [Role] (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL
);

CREATE TABLE Tag (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX)
);

CREATE TABLE Fire_equipments (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(MAX)
);

CREATE TABLE Amenities (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(MAX)
);

CREATE TABLE Tag_for_news (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(MAX) NOT NULL
);

CREATE TABLE Topic (
    id INT PRIMARY KEY IDENTITY(1,1),
    parent_topic_id INT FOREIGN KEY REFERENCES Topic(id),
    topic_name NVARCHAR(MAX) NOT NULL
);

-- 2. Bảng "House_owner" vì được tham chiếu trong bảng "House"
CREATE TABLE House_owner (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(MAX),
    address NVARCHAR(MAX),
    phone NVARCHAR(MAX),
    email VARCHAR(100),
    gender NVARCHAR(1) CHECK (gender IN ('F', 'M')),
    dob VARCHAR(100)
);

-- 3. Tạo bảng "Account" vì các bảng khác sẽ tham chiếu tới nó
CREATE TABLE Account (
    id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(100),
    password VARCHAR(100),
    verify BIT NOT NULL,
    email VARCHAR(100),
    avatar_image_id INT,
    role_id INT FOREIGN KEY REFERENCES Role(id),
    registration_date VARCHAR(100)
);

CREATE TABLE Token (
    id UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY, 
    token NVARCHAR(255) NOT NULL,                   
    expiry_date DATETIME NOT NULL,                 
    account_id INT,                                 
    CONSTRAINT FK_Account_Token FOREIGN KEY (account_id) REFERENCES Account(id) 
);


-- 4. Bảng "House" vì các bảng Image và Document sẽ tham chiếu tới
CREATE TABLE House (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(MAX),
    ward NVARCHAR(MAX) NOT NULL,
    district NVARCHAR(MAX) NOT NULL,
    province NVARCHAR(MAX) NOT NULL,
    location NVARCHAR(MAX) NOT NULL,
    land_space DECIMAL(12, 4) NOT NULL,
    living_space DECIMAL(12, 4) NOT NULL,
    number_bed_room INT,
    number_bath INT,
    description NVARCHAR(MAX),
    coordinates_on_map NVARCHAR(MAX),
    available_status BIT,
    updated_date VARCHAR(100),
    updated_by INT,
    owner_by INT FOREIGN KEY REFERENCES House_owner(id)
);

-- 5. Bảng Image và Document (phụ thuộc vào House)
CREATE TABLE [Image] (
    id INT PRIMARY KEY IDENTITY(1,1),
    house_id INT FOREIGN KEY REFERENCES House(id),
    path VARCHAR(255) NOT NULL,
    upload_date VARCHAR(100)
);

CREATE TABLE Document (
    id INT PRIMARY KEY IDENTITY(1,1),
    house_id INT FOREIGN KEY REFERENCES House(id),
    name NVARCHAR(MAX),
    path VARCHAR(255) NOT NULL,
    upload_date VARCHAR(100)
);

-- 6. Các bảng liên quan đến Account
CREATE TABLE [Notification] (
    id INT PRIMARY KEY IDENTITY(1,1),
    content NVARCHAR(MAX) NOT NULL,
    created_date VARCHAR(100),
    read_status NVARCHAR(100)
);
CREATE TABLE Account_Notification (
    notification_id INT FOREIGN KEY REFERENCES Notification(id),
    account_id INT FOREIGN KEY REFERENCES Account(id),
    PRIMARY KEY (notification_id, account_id)
);

-- 7. Các bảng phụ thuộc vào Account và Role
CREATE TABLE Staff (
    id INT PRIMARY KEY FOREIGN KEY REFERENCES Account(id),
    full_name NVARCHAR(100),
    gender NVARCHAR(1) CHECK (gender IN ('F', 'M')),
    date_of_birth VARCHAR(100),
    address NVARCHAR(MAX),
    phone_number VARCHAR(10)
);

CREATE TABLE Customer (
    id INT PRIMARY KEY FOREIGN KEY REFERENCES Account(id),
    full_name NVARCHAR(100),
    gender NVARCHAR(1) CHECK (gender IN ('F', 'M')),
    date_of_birth VARCHAR(100),
    address NVARCHAR(MAX),
    phone_number VARCHAR(10),
    citizen_identification VARCHAR(50),
    id_issuance_date VARCHAR(100),
    id_issuance_place NVARCHAR(MAX),
    id_card_front_image_id INT FOREIGN KEY REFERENCES Image(id),
    id_card_back_image_id INT FOREIGN KEY REFERENCES Image(id),
);

-- 8. Bảng chứa tiện ích và thiết bị phòng cháy của House
CREATE TABLE Fire_equipments_House (
    id INT PRIMARY KEY IDENTITY(1,1),
    fire_id INT FOREIGN KEY REFERENCES Fire_equipments(id),
    house_id INT FOREIGN KEY REFERENCES House(id)
);

CREATE TABLE Amenities_House (
    id INT PRIMARY KEY IDENTITY(1,1),
    amenities_id INT FOREIGN KEY REFERENCES Amenities(id),
    house_id INT FOREIGN KEY REFERENCES House(id)
);

-- 9. Bảng lưu các thông tin đăng ký và hợp đồng nhà thuê
CREATE TABLE House_register (
    id INT PRIMARY KEY IDENTITY(1,1),
    house_id INT NOT NULL FOREIGN KEY REFERENCES House(id),
    tenant_id INT NOT NULL FOREIGN KEY REFERENCES Account(id),
    result NVARCHAR(100),
    deposit_status NVARCHAR(100),
    registration_time VARCHAR(100)
);

CREATE TABLE Contract (
    id INT PRIMARY KEY IDENTITY(1,1),
    house_id INT FOREIGN KEY REFERENCES House(id),
    owner_by INT FOREIGN KEY REFERENCES House_owner(id),
    tenant_id INT FOREIGN KEY REFERENCES Account(id),
    created_by INT FOREIGN KEY REFERENCES Account(id), --nhan vien tao Contract luc ban dau
    rule_document INT FOREIGN KEY REFERENCES Document(id),
    price BIGINT,
    deposit BIGINT,
    lease_duration_day DECIMAL,
    move_in_date VARCHAR(100),
    signed_date VARCHAR(100),
    created_date VARCHAR(100)
);

-- 10. Các bảng lưu tag và liên kết tag với nhà thuê và tin tức
CREATE TABLE Tag_House (
    id INT PRIMARY KEY IDENTITY(1,1),
    tag_id INT FOREIGN KEY REFERENCES Tag(id),
    house_id INT FOREIGN KEY REFERENCES House(id)
);

CREATE TABLE News (
    id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(MAX) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    cover_photo_id INT FOREIGN KEY REFERENCES Image(id),
    created_date VARCHAR(100),
    staff_id INT FOREIGN KEY REFERENCES Account(id)
);

CREATE TABLE News_Tag_for_news (
tag_id INT NOT NULL FOREIGN KEY REFERENCES Tag_for_news(id),
    news_id INT NOT NULL FOREIGN KEY REFERENCES News(id),
    PRIMARY KEY (tag_id, news_id)
);

-- 11. Bảng câu hỏi thuộc các chủ đề hỗ trợ khách hàng
CREATE TABLE Question (
    id INT PRIMARY KEY IDENTITY(1,1),
    topic_id INT NOT NULL FOREIGN KEY REFERENCES Topic(id),
    question NVARCHAR(MAX) NOT NULL,
    answer NVARCHAR(MAX) NOT NULL
);



-- 12. Bổ sung khóa ngoại cho cột updated_by trong bảng House sau khi tất cả bảng đã được tạo
ALTER TABLE House
ADD CONSTRAINT FK_House_UpdatedBy FOREIGN KEY (updated_by) REFERENCES Account(id);

ALTER TABLE Account 
ADD CONSTRAINT FK_Account_UpdatedBy FOREIGN KEY (avatar_image_id) REFERENCES [dbo].[Image](id);


---------------------------------DU LIEU CO DINH (KHONG DUOC XOA)-------------------------------------------------
USE project_house_rental_hsf301_assignment;

-- 1. Thêm dữ liệu mẫu vào bảng Role
INSERT INTO Role (name) VALUES 
('ROLE_CUSTOMER'),
('ROLE_HOUSE_LISTING_AGENT'),
('ROLE_CUSTOMER_CARE'),
('ROLE_NEWS_WRITER');

-- 2. Thêm dữ liệu mẫu vào bảng Tag
INSERT INTO Tag (name, description) VALUES 
('Luxury', 'High-end properties with premium features'),
('Pet Friendly', 'Allows pets'),
('Close to public transport', 'Near train and bus stations');

-- 3. Thêm dữ liệu mẫu vào bảng Fire_equipments
INSERT INTO Fire_equipments (name) VALUES 
('Smoke Detector'),
('Fire Extinguisher'),
('Sprinkler System');

-- 4. Thêm dữ liệu mẫu vào bảng Amenities
INSERT INTO Amenities (name) VALUES 
('Gym'),
('Swimming Pool'),
('Wi-Fi'),
('Parking');

-- 5. Thêm dữ liệu mẫu vào bảng Tag_for_news
INSERT INTO Tag_for_news (name) VALUES 
('Promotion'), 
('New Launch'), 
('Tips'), 
('Update');

-- 6. Thêm dữ liệu mẫu vào bảng Topic
INSERT INTO Topic (topic_name, parent_topic_id) VALUES 
('Rental Assistance', NULL),
('Lease Terms', 1),
('Payment Information', NULL),
('Fire Safety', NULL);

-- 7. Thêm dữ liệu mẫu vào bảng House_owner
INSERT INTO House_owner (name, address, phone, email, gender, dob) VALUES 
('John Doe', '123 Elm St, Cityville', '1234567890', 'john.doe@example.com', 'M', '30/10/2004'),
('Jane Smith', '456 Oak St, Townsville', '0987654321', 'jane.smith@example.com', 'F', '29/10/2004');

-- 9. Thêm dữ liệu mẫu vào bảng Image
INSERT INTO Image (house_id, path, upload_date) VALUES 
(null, '/images/avatar_default.jpg', '2024-09-26 15:38:54.468');

-- 11. Thêm dữ liệu mẫu vào bảng Account
INSERT INTO Account (username, password, verify, email, avatar_image_id, role_id, registration_date) 
VALUES 
('customer1', '$2a$10$43UPoYJoq5cJT.U6bSrZPOAQ4K.GrN8F5JzhGdBcxy.ZfFpvrsUAi', 1, 'alexpeter@example.com', 1, 1, '30/10/2024'),
('admin', '$2a$10$43UPoYJoq5cJT.U6bSrZPOAQ4K.GrN8F5JzhGdBcxy.ZfFpvrsUAi', 1, 'johndoe@example.com', 1, 2, '30/10/2024'),
('houselistingagent', '$2a$10$43UPoYJoq5cJT.U6bSrZPOAQ4K.GrN8F5JzhGdBcxy.ZfFpvrsUAi', 1, 'chickenrice@example.com', 1, 2, '30/10/2024'),
('customercare', '$2a$10$43UPoYJoq5cJT.U6bSrZPOAQ4K.GrN8F5JzhGdBcxy.ZfFpvrsUAi', 1, 'banhmy@example.com', 1, 3, '30/10/2024'),
('newswriter', '$2a$10$43UPoYJoq5cJT.U6bSrZPOAQ4K.GrN8F5JzhGdBcxy.ZfFpvrsUAi', 1, 'meomeo@example.com', 1, 4, '30/10/2024');

-- 12. Thêm dữ liệu mẫu vào bảng Notification
INSERT INTO Notification (content, created_date, read_status) VALUES 
('Welcome to our rental service!', '30/10/2024', 'Unread'),
('Your lease is ready for signing.', '30/10/2024', 'Unread');

-- 13. Thêm dữ liệu mẫu vào bảng Account_Notification
INSERT INTO Account_Notification (notification_id, account_id) VALUES 
(1, 1), 
(2, 1);

-- 14. Thêm dữ liệu mẫu vào bảng Staff
INSERT INTO Staff (id, full_name, gender, date_of_birth, address, phone_number) VALUES 
(2, N'Bùi Minh Chiến', 'M', '18/10/2004', '123 Elm St, Cityville', '1234567890'),
(3, N'Thái Đình Giáp', 'M', '18/10/2004', '123 Elm St, Cityville', '1234567890'),
(4, N'Phạm Việt Tùng', 'M', '18/10/2004', '123 Elm St, Cityville', '1234567890'),
(5, N'Lê Đoàn Đức Chung', 'M', '18/10/2004', '123 Elm St, Cityville', '1234567890');

--15. Thêm dữ liệu mẫu vào bảng Customer

INSERT INTO [dbo].[Customer] 
       ([account_id], 
       [full_name], 
       [gender], 
       [date_of_birth], 
       [address], 
       [phone_number], 
       [citizen_identification], 
       [id_issuance_date], 
       [id_issuance_place], 
       [id_card_front_image_id], 
       [id_card_back_image_id])
VALUES 
       (1,  -- id      ,
	   'John Doe',  -- full_name
       'M',  -- gender
       '1990-01-01',  -- date_of_birth
       '123 Main St',  -- address
       '1234567890',  -- phone_number
       'ID123456',  -- citizen_identification
       '2010-01-01',  -- id_issuance_date
       'City Hall',  -- id_issuance_place
      null,  -- id_card_front_image_id
      null);  -- id_card_back_image_id
-- 23. Thêm dữ liệu mẫu vào bảng Question
INSERT INTO Question (topic_id, question, answer) 
VALUES 
(1, 'How do I register for a lease?', 'You can register by visiting the property page and clicking the "Register" button.');

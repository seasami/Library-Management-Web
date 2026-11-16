USE `quan_ly_thu_vien`;

-- 1. ROLES
INSERT INTO `roles` (`role_id`, `role_name`, `description`) VALUES
('R01', 'Admin', 'System administrator'),
('R02', 'Manager', 'Library manager'),
('R03', 'Librarian', 'Library staff'),
('R04', 'Reader', 'Library reader');

-- 2. USERS
INSERT INTO `users`
(`user_id`, `role_id`, `created_at`, `username`, `email`, `password_hash`, `full_name`, `phone`, `status`)
VALUES
('U001', 'R01', '2026-01-01 08:00:00', 'admin', 'admin@library.test', 'hash_admin_placeholder', 'Nguyen Minh An', '0900000001', 'active'),
('U002', 'R02', '2026-01-02 08:00:00', 'manager01', 'manager01@library.test', 'hash_manager_placeholder', 'Tran Thu Ha', '0900000002', 'active'),
('U003', 'R02', '2026-01-03 08:00:00', 'manager02', 'manager02@library.test', 'hash_manager_placeholder', 'Le Quoc Bao', '0900000003', 'active'),
('U004', 'R03', '2026-01-04 08:00:00', 'librarian01', 'librarian01@library.test', 'hash_librarian_placeholder', 'Pham Ngoc Linh', '0900000004', 'active'),
('U005', 'R03', '2026-01-05 08:00:00', 'librarian02', 'librarian02@library.test', 'hash_librarian_placeholder', 'Vo Thanh Nam', '0900000005', 'active'),
('U006', 'R03', '2026-01-06 08:00:00', 'librarian03', 'librarian03@library.test', 'hash_librarian_placeholder', 'Do My Duyen', '0900000006', 'active'),
('U007', 'R04', '2026-02-01 08:00:00', 'reader01', 'reader01@library.test', 'hash_reader_placeholder', 'Nguyen Van Khang', '0900000007', 'active'),
('U008', 'R04', '2026-02-02 08:00:00', 'reader02', 'reader02@library.test', 'hash_reader_placeholder', 'Tran Ngoc Mai', '0900000008', 'active'),
('U009', 'R04', '2026-02-03 08:00:00', 'reader03', 'reader03@library.test', 'hash_reader_placeholder', 'Le Hoang Phuc', '0900000009', 'active'),
('U010', 'R04', '2026-02-04 08:00:00', 'reader04', 'reader04@library.test', 'hash_reader_placeholder', 'Pham Gia Han', '0900000010', 'active'),
('U011', 'R04', '2026-02-05 08:00:00', 'reader05', 'reader05@library.test', 'hash_reader_placeholder', 'Vo Minh Tri', '0900000011', 'restricted'),
('U012', 'R04', '2026-02-06 08:00:00', 'reader06', 'reader06@library.test', 'hash_reader_placeholder', 'Bui Khanh Vy', '0900000012', 'active'),
('U013', 'R04', '2026-02-07 08:00:00', 'reader07', 'reader07@library.test', 'hash_reader_placeholder', 'Dang Tuan Kiet', '0900000013', 'locked'),
('U014', 'R04', '2026-02-08 08:00:00', 'reader08', 'reader08@library.test', 'hash_reader_placeholder', 'Hoang Thuy Duong', '0900000014', 'active'),
('U015', 'R04', '2026-02-09 08:00:00', 'reader09', 'reader09@library.test', 'hash_reader_placeholder', 'Ngo Duc Huy', '0900000015', 'active'),
('U016', 'R04', '2026-02-10 08:00:00', 'reader10', 'reader10@library.test', 'hash_reader_placeholder', 'Mai Phuong Thao', '0900000016', 'active');

-- 3. PASSWORD RESET TOKENS
INSERT INTO `password_reset_tokens`
(`reset_token_id`, `user_id`, `token_hash`, `expires_at`, `used_at`, `created_at`)
VALUES
('T001', 'U001', REPEAT('a', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:00:00'),
('T002', 'U002', REPEAT('b', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:01:00'),
('T003', 'U003', REPEAT('c', 64), '2026-11-17 08:00:00', '2026-11-16 09:00:00', '2026-11-16 08:02:00'),
('T004', 'U004', REPEAT('d', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:03:00'),
('T005', 'U005', REPEAT('e', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:04:00'),
('T006', 'U006', REPEAT('f', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:05:00'),
('T007', 'U007', REPEAT('1', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:06:00'),
('T008', 'U008', REPEAT('2', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:07:00'),
('T009', 'U009', REPEAT('3', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:08:00'),
('T010', 'U010', REPEAT('4', 64), '2026-11-17 08:00:00', NULL, '2026-11-16 08:09:00');

-- 4. ROLE-SPECIFIC PROFILES
INSERT INTO `managers` (`user_id`, `employee_code`, `office_location`, `appointed_at`) VALUES
('U002', 'MGR001', 'Main office', '2026-01-02'),
('U003', 'MGR002', 'Branch office', '2026-01-03');

INSERT INTO `librarians` (`user_id`, `warehouse_code`, `work_shift`) VALUES
('U004', 'WH001', 'Morning'),
('U005', 'WH001', 'Afternoon'),
('U006', 'WH002', 'Full-time');

INSERT INTO `readers`
(`user_id`, `date_of_birth`, `created_at`, `updated_at`, `citizen_id`)
VALUES
('U007', '2002-04-12', '2026-02-01 08:00:00', '2026-02-01 08:00:00', '079200000007'),
('U008', '2001-08-20', '2026-02-02 08:00:00', '2026-02-02 08:00:00', '079200000008'),
('U009', '2003-01-15', '2026-02-03 08:00:00', '2026-02-03 08:00:00', '079200000009'),
('U010', '1999-11-30', '2026-02-04 08:00:00', '2026-02-04 08:00:00', '079200000010'),
('U011', '2000-05-05', '2026-02-05 08:00:00', '2026-02-05 08:00:00', '079200000011'),
('U012', '2004-09-09', '2026-02-06 08:00:00', '2026-02-06 08:00:00', '079200000012'),
('U013', '2001-12-12', '2026-02-07 08:00:00', '2026-02-07 08:00:00', '079200000013'),
('U014', '1998-03-18', '2026-02-08 08:00:00', '2026-02-08 08:00:00', '079200000014'),
('U015', '2002-07-22', '2026-02-09 08:00:00', '2026-02-09 08:00:00', '079200000015'),
('U016', '2003-10-10', '2026-02-10 08:00:00', '2026-02-10 08:00:00', '079200000016');

-- 5. SUBSCRIPTION PLANS
INSERT IGNORE INTO `subscription_plans`
(`plan_id`, `plan_name`, `duration_months`, `is_active`)
VALUES
('PLAN6M', '6-month subscription', 6, 1),
('PLAN12M', '12-month subscription', 12, 1);

-- 6. READER SUBSCRIPTIONS
INSERT INTO `reader_subscriptions`
(`subscription_id`, `reader_id`, `plan_id`, `started_at`, `expires_at`, `subscription_status`, `created_at`)
VALUES
('SUB001', 'U008', 'PLAN6M', '2026-08-01 00:00:00', '2027-02-01 00:00:00', 'active', '2026-08-01 00:00:00'),
('SUB002', 'U010', 'PLAN12M', '2026-01-15 00:00:00', '2027-01-15 00:00:00', 'active', '2026-01-15 00:00:00'),
('SUB003', 'U012', 'PLAN6M', '2026-09-01 00:00:00', '2027-03-01 00:00:00', 'active', '2026-09-01 00:00:00'),
('SUB004', 'U014', 'PLAN12M', '2026-03-01 00:00:00', '2027-03-01 00:00:00', 'active', '2026-03-01 00:00:00'),
('SUB005', 'U016', 'PLAN6M', '2026-10-01 00:00:00', '2027-04-01 00:00:00', 'active', '2026-10-01 00:00:00'),
('SUB006', 'U009', 'PLAN6M', '2026-01-01 00:00:00', '2026-07-01 00:00:00', 'active', '2026-01-01 00:00:00');

-- 7. AUDIT LOGS
INSERT INTO `audit_logs`
(`log_id`, `actor_user_id`, `action`, `entity_type`, `entity_id`, `reason`, `old_value`, `new_value`, `created_at`)
VALUES
('L001', 'U001', 'CREATE_USER', 'users', 'U007', 'Create reader account', NULL, JSON_OBJECT('status', 'active'), '2026-02-01 08:10:00'),
('L002', 'U002', 'UPDATE_USER_STATUS', 'users', 'U011', 'Restrict reader account', JSON_OBJECT('status', 'active'), JSON_OBJECT('status', 'restricted'), '2026-10-01 09:00:00'),
('L003', 'U002', 'UPDATE_USER_STATUS', 'users', 'U013', 'Lock reader account', JSON_OBJECT('status', 'active'), JSON_OBJECT('status', 'locked'), '2026-10-02 09:00:00'),
('L004', 'U004', 'CREATE_BOOK', 'books', 'B001', 'Add new book', NULL, JSON_OBJECT('title', 'The Little Prince'), '2026-03-01 10:00:00'),
('L005', 'U004', 'CREATE_BORROW_SLIP', 'borrow_slips', 'S001', 'Reader borrowed a book', NULL, JSON_OBJECT('slip_status', 'open'), '2026-11-14 09:00:00'),
('L006', 'U005', 'CONFIRM_RETURN', 'borrow_details', 'D002', 'Book returned in good condition', NULL, JSON_OBJECT('return_condition', 'good'), '2026-11-07 10:00:00'),
('L007', 'U006', 'CREATE_SUBSCRIPTION', 'reader_subscriptions', 'SUB001', 'Activate 6-month plan', NULL, JSON_OBJECT('subscription_status', 'active'), '2026-08-01 00:00:00'),
('L008', 'U004', 'UPDATE_BORROWING_RULE', 'borrowing_rules', 'REG01', 'Set regular reader limit', NULL, JSON_OBJECT('max_books', 2, 'loan_days', 7), '2026-10-01 08:00:00'),
('L009', 'U004', 'UPDATE_BORROWING_RULE', 'borrowing_rules', 'SUB01', 'Set subscriber reader limit', NULL, JSON_OBJECT('max_books', 5, 'loan_days', 14), '2026-10-01 08:05:00'),
('L010', 'U003', 'UPDATE_BOOK_STATUS', 'books', 'B010', 'Temporarily deactivate book', JSON_OBJECT('status', 'active'), JSON_OBJECT('status', 'inactive'), '2026-10-10 11:00:00');

-- 8. BOOK CATALOG
INSERT INTO `authors` (`author_id`, `author_name`) VALUES
('A001', 'Antoine de Saint-Exupery'),
('A002', 'Nguyen Nhat Anh'),
('A003', 'Nam Cao'),
('A004', 'To Hoai'),
('A005', 'J. K. Rowling'),
('A006', 'George Orwell'),
('A007', 'Paulo Coelho'),
('A008', 'Haruki Murakami'),
('A009', 'Dale Carnegie'),
('A010', 'Nguyen Du');

INSERT INTO `categories` (`category_id`, `category_name`) VALUES
('C001', 'Literature'),
('C002', 'Children'),
('C003', 'Science'),
('C004', 'History'),
('C005', 'Technology'),
('C006', 'Psychology'),
('C007', 'Fantasy'),
('C008', 'Economics'),
('C009', 'Language'),
('C010', 'Poetry');

INSERT INTO `publishers` (`publisher_id`, `publisher_name`) VALUES
('P001', 'NXB Kim Dong'),
('P002', 'NXB Tre'),
('P003', 'NXB Van Hoc'),
('P004', 'NXB Giao Duc Viet Nam'),
('P005', 'Bloomsbury'),
('P006', 'Penguin Books'),
('P007', 'NXB Lao Dong'),
('P008', 'NXB The Gioi'),
('P009', 'NXB Tong Hop TP.HCM'),
('P010', 'Oxford University Press');

INSERT INTO `books`
(`book_id`, `title`, `category_id`, `secondary_category_id`, `publisher_id`, `publication_year`, `min_reader_age`, `status`, `description`)
VALUES
('B001', 'The Little Prince', 'C001', 'C002', 'P001', 1943, 0, 'active', 'A philosophical tale for readers of all ages.'),
('B002', 'Mat Biec', 'C001', NULL, 'P002', 1990, 12, 'active', 'A Vietnamese novel by Nguyen Nhat Anh.'),
('B003', 'Chi Pheo', 'C001', NULL, 'P003', 1941, 16, 'active', 'A classic Vietnamese short story.'),
('B004', 'De Men Phieu Luu Ky', 'C002', 'C001', 'P001', 1941, 0, 'active', 'A well-known Vietnamese children story.'),
('B005', 'Harry Potter and the Philosopher Stone', 'C007', 'C002', 'P005', 1997, 10, 'active', 'The first book in the Harry Potter series.'),
('B006', 'Animal Farm', 'C001', NULL, 'P006', 1945, 14, 'active', 'A political satire novella.'),
('B007', 'The Alchemist', 'C001', NULL, 'P006', 1988, 12, 'active', 'A novel about following one’s dreams.'),
('B008', 'Norwegian Wood', 'C001', NULL, 'P006', 1987, 18, 'active', 'A novel by Haruki Murakami.'),
('B009', 'How to Win Friends and Influence People', 'C006', NULL, 'P007', 1936, 16, 'active', 'A personal development classic.'),
('B010', 'Truyen Kieu', 'C010', 'C001', 'P003', 1820, 0, 'inactive', 'A Vietnamese epic poem.');

INSERT INTO `book_authors` (`book_id`, `author_id`) VALUES
('B001', 'A001'),
('B002', 'A002'),
('B003', 'A003'),
('B004', 'A004'),
('B005', 'A005'),
('B006', 'A006'),
('B007', 'A007'),
('B008', 'A008'),
('B009', 'A009'),
('B010', 'A010');

INSERT INTO `book_copies` (`copy_id`, `book_id`, `copy_code`, `copy_status`) VALUES
('CP001', 'B001', 'LIB-B001-001', 'available'),
('CP002', 'B002', 'LIB-B002-001', 'available'),
('CP003', 'B003', 'LIB-B003-001', 'available'),
('CP004', 'B004', 'LIB-B004-001', 'available'),
('CP005', 'B005', 'LIB-B005-001', 'available'),
('CP006', 'B006', 'LIB-B006-001', 'available'),
('CP007', 'B007', 'LIB-B007-001', 'available'),
('CP008', 'B008', 'LIB-B008-001', 'available'),
('CP009', 'B009', 'LIB-B009-001', 'available'),
('CP010', 'B010', 'LIB-B010-001', 'available');

-- 9. BORROWING RULES
INSERT INTO `borrowing_rules`
(`rule_id`, `rule_name`, `reader_type`, `max_books`, `loan_days`, `min_age_for_restricted_books`, `is_active`, `updated_by`)
VALUES
('REG01', 'Regular reader', 'regular', 2, 7, 0, 1, 'U004'),
('SUB01', 'Subscriber reader', 'subscriber', 5, 14, 0, 1, 'U004');

-- 10. BORROWING HISTORY
INSERT INTO `borrow_slips`
(`slip_id`, `reader_id`, `created_by`, `borrowed_at`, `slip_status`, `note`)
VALUES
('S001', 'U007', 'U004', '2026-11-14 09:00:00', 'open', 'Regular reader - current loan'),
('S002', 'U007', 'U004', '2026-10-31 09:00:00', 'completed', 'Returned on time'),
('S003', 'U008', 'U005', '2026-11-14 10:00:00', 'open', 'Subscriber - current loan'),
('S004', 'U009', 'U005', '2026-10-20 10:00:00', 'completed', 'Expired subscription; regular borrowing period'),
('S005', 'U010', 'U006', '2026-11-10 08:30:00', 'open', 'Subscriber - current loan'),
('S006', 'U012', 'U004', '2026-11-12 13:00:00', 'open', 'Subscriber - current loan'),
('S007', 'U014', 'U006', '2026-10-25 14:00:00', 'completed', 'Returned in good condition'),
('S008', 'U015', 'U005', '2026-11-15 09:30:00', 'open', 'Regular reader - current loan'),
('S009', 'U016', 'U004', '2026-10-28 15:00:00', 'completed', 'Returned in good condition'),
('S010', 'U008', 'U005', '2026-11-15 10:00:00', 'open', 'Subscriber - second current loan');

INSERT INTO `borrow_details`
(`detail_id`, `slip_id`, `copy_id`, `due_at`, `returned_at`, `return_condition`, `return_confirmed_by`, `note`)
VALUES
('D001', 'S001', 'CP001', '2026-11-21 09:00:00', NULL, NULL, NULL, NULL),
('D002', 'S002', 'CP002', '2026-11-07 09:00:00', '2026-11-06 10:00:00', 'good', 'U005', NULL),
('D003', 'S003', 'CP003', '2026-11-28 10:00:00', NULL, NULL, NULL, NULL),
('D004', 'S004', 'CP004', '2026-10-27 10:00:00', '2026-10-25 10:00:00', 'good', 'U005', NULL),
('D005', 'S005', 'CP005', '2026-11-24 08:30:00', NULL, NULL, NULL, NULL),
('D006', 'S006', 'CP006', '2026-11-26 13:00:00', NULL, NULL, NULL, NULL),
('D007', 'S007', 'CP007', '2026-11-08 14:00:00', '2026-11-05 14:00:00', 'good', 'U006', NULL),
('D008', 'S008', 'CP008', '2026-11-22 09:30:00', NULL, NULL, NULL, NULL),
('D009', 'S009', 'CP009', '2026-11-11 15:00:00', '2026-11-10 15:00:00', 'good', 'U004', NULL),
('D010', 'S010', 'CP010', '2026-11-29 10:00:00', NULL, NULL, NULL, 'Current loan for subscriber reader.');
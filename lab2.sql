use master

go
CREATE database Assignment2_LeMinhTuan

go
use Assignment2_LeMinhTuan

go
create table Accounts (
	email varchar(300) primary key,
	name varchar(100),
	role varchar(50),
	password varchar(200),
	status varchar(50)
)

create table Subjects (
	id varchar(30) primary key,
	name varchar(50),
	number_of_question_in_quiz int,
	time_of_each_quiz_minute int
)

create table QuestionsSource(
	id int IDENTITY(1,1) primary key,
	question_content varchar(500),
	create_date date,
	subject_id varchar(30),
	status bit
)

create table AnswersSource(
	id int IDENTITY(1,1) primary key,
	answer_content varchar(500),
	create_date date,
	question_id int,
	is_correct_answer bit,
)

create table Quizes(
	id int IDENTITY(1,1) primary key,
	user_take_quiz varchar(300) foreign key references Accounts(email),
	subject_id varchar(30) foreign key references Subjects(id),
	number_of_question int,
	number_correct_answer int,
	date_take_quiz datetime
)

create table QuestionsInQuiz(
	id int IDENTITY(1,1) primary key,
	question_content varchar(500),
	quiz_id int
)

create table AnswersInQuiz(
	answer_id int IDENTITY(1,1) primary key,
	question_id int,
	answer_choose varchar(500),
	answer_correct varchar(500)
)

go
ALTER TABLE QuestionsSource
	ADD CONSTRAINT fk_Subjects_QuestionsSource
	FOREIGN KEY (subject_id)
	REFERENCES Subjects (id);

go
ALTER TABLE AnswersSource
	ADD CONSTRAINT fk_Answers_QuestionsSource
	FOREIGN KEY (question_id)
	REFERENCES QuestionsSource (id);

go
ALTER TABLE QuestionsInQuiz
	ADD CONSTRAINT fk_Quizes_QuestionsInQuiz
	FOREIGN KEY (quiz_id)
	REFERENCES Quizes (id);

go
ALTER TABLE AnswersInQuiz
	ADD CONSTRAINT fk_AnswersInQuiz_QuestionsInQuiz
	FOREIGN KEY (question_id)
	REFERENCES QuestionsInQuiz (id);

go
insert into Subjects(id, name, number_of_question_in_quiz, time_of_each_quiz_minute) values('PRO192', 'Java OOP', 5, 1)

insert into Subjects(id, name, number_of_question_in_quiz, time_of_each_quiz_minute) values('SWR302', 'Software Requirement', 7, 5)

insert into Subjects(id, name, number_of_question_in_quiz, time_of_each_quiz_minute) values('SWT301', 'Software Testing', 7, 3)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('Which of the following is not OOPS concept in Java ?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Inheritance', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Encapsulation', '7/2/2021',  @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Polymorphism', '7/2/2021',  @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Compilation', '7/2/2021',  @result , 1)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('Which of the following is a type of polymorphism in Java ?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Compile time polymorphism', '7/2/2021', @result , 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Execution time polymorphism', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Multiple polymorphism', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Multilevel polymorphism', '7/2/2021', @result , 0)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('When does method overloading is determined ?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('At run time', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('At compile time', '7/2/2021', @result, 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('At coding time', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('At execution time', '7/2/2021', @result, 0)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('Which concept of Java is a way of converting real world objects in terms of class? ', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource
insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Polymorphism', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Encapsulation', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Abstraction', '7/2/2021', @result, 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Inheritance', '7/2/2021', @result, 0)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('What is it called if an object has its own lifecycle and there is no owner?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Aggregation', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Composition', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Encapsulation', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Association', '7/2/2021', @result, 1)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('What is it called where object has its own lifecycle and child object cannot belong to another parent object?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Aggregation', '7/2/2021', @result , 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Composition', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Encapsulation', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Association', '7/2/2021', @result, 0)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('What would be the behaviour if this() and super() used in a method ?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Runtime error', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Throws exception', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('compile time error', '7/2/2021', @result, 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Runs successfully', '7/2/2021', @result, 0)

go
DECLARE @result int = 1;
insert into QuestionsSource(question_content, create_date, subject_id, status)
		values('What is true about constructor?', '7/2/2021', 'PRO192', 1)

select @result = CAST(max(id) as int)  from QuestionsSource

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('It can contain return type', '7/2/2021', @result , 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('It can take any number of parameters', '7/2/2021', @result, 1)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('It can have any non access modifiers', '7/2/2021', @result, 0)

insert into AnswersSource(answer_content, create_date, question_id, is_correct_answer)
		values('Constructor cannot throw an exception', '7/2/2021', @result, 0)
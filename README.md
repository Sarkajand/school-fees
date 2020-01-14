# School-fees
School-fees is an app for record payments, it calculate fees and give data for accountancy. 



# Motivation
I started learning programming and I wanted to work on something usefull, so I took a challenge to write an app to help primary school with keeping the records and with calculating the school fees. The app is localized to Czech language and functions are according to customers requirements. 

#### all data in aplication and in database are dummy data for testing and presentation

User can see data about students and can manage them on the first tab. Here are added new students or edited existing students, also removing student is possible. Students can be sorted by school stage (preschool and primary school). Another option is show only students from one class by pick the class in the choice box.  
![Bez názvu](https://user-images.githubusercontent.com/57794464/72364862-84241280-36f7-11ea-840d-4fb398d7fc17.png)

Second tab is for bank statements, they are read and saved with all transactions automaticaly from a csv file.
![importBankStatement](https://user-images.githubusercontent.com/57794464/72365505-abc7aa80-36f8-11ea-85b1-5ef3329c7084.png)

Third tab is for transactions. User sees all transactions and can manage them. They can be sorted by columns, sorting by date and by class was demanded by the customer. It is posible to find all transactions for one student by his variable symbol. 
![transactions](https://user-images.githubusercontent.com/57794464/72366504-ac614080-36fa-11ea-9cd1-9b84fd00a761.png)

Last tab is summary. Here are put together some informations about students with individually calculated fees. Here it is possible to sort students by stage or class too. These informations are also data for the accountant and it is posible to export this data to csv file.  
![summaryTab](https://user-images.githubusercontent.com/57794464/72367025-b768a080-36fb-11ea-9079-84e36fd6f216.png)

Options for a backup database and for open window for classes are in menu. Classes are usualy edited only once per year, so there is no tab in main window for classes. 

# Technologies
Project is written in Java with JavaFx framework. I use Maven to build project. I choose SQLite database because it is fast and suitable for my project. It is easy to do a backup with one file database, it doesn't take too much space and it is sufficient for the amount of data expected. 

# Project status and future
The app is working fine, but I am stil learning and with new knowledge I improve the app and learn even more during the process. 

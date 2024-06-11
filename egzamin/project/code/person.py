class Person:
    def __init__(self, name, surname):
        self.name = name
        self.surname = surname

    def display_info(self):
        print(f"Name: {self.name}")
        print(f"Surname: {self.surname}")


class Student(Person):
    def __init__(self, name, surname, student_id):
        super().__init__(name, surname)
        self.student_id = student_id

    def display_info(self):
        super().display_info()
        print(f"Student ID: {self.student_id}")

    def study(self):
        print(f"{self.name} is studying.")


class Employee(Person):
    def __init__(self, name, surname, employee_id):
        super().__init__(name, surname)
        self.employee_id = employee_id

    def display_info(self):
        super().display_info()
        print(f"Employee ID: {self.employee_id}")

    def work(self):
        print(f"{self.name} is working.")


student = Student("Johnny", "Bravo", "212")
student.display_info()
student.study()

print()

employee = Employee("Alicja", "Kot", "213")
employee.display_info()
employee.work()

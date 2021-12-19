package com.example.bai13spring;

import com.example.bai13spring.entities.*;
import com.example.bai13spring.exceptions.BirthdayException;
import com.example.bai13spring.exceptions.EmailException;
import com.example.bai13spring.exceptions.FullNameException;
import com.example.bai13spring.exceptions.PhoneException;
import com.example.bai13spring.repositories.CertificateRepository;
import com.example.bai13spring.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public int checkInt(int min, int max){
        int num;
        Scanner sc = new Scanner(System.in);
        do{
            try{
                num = Integer.parseInt(sc.nextLine());
                if(num>=min&&num<=max) return num;
                else System.out.println("Out of range, please enter in range of "+min+" - "+max);
            }
            catch(NumberFormatException ex){ /*tai sao la number format exception*/
                System.out.println("Invalid input, enter again ");
            }
        } while(true);
    }

    public String checkName(String mess){
        String fullName;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            try{
                fullName = sc.nextLine().trim();
                fullName = fullName.replaceAll("\\s+"," ");
                if(fullName.matches("^[a-zA-Z\\s]*$")) return fullName;
                else throw new FullNameException();
            }
            catch(FullNameException ex){ /**/
                System.out.println(ex.getMessage());
            }
        } while(true);
    }

    public Date checkDate(String mess){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            try{
                String birth = sc.nextLine();
                return sdf.parse(birth);
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        } while(true);
    }

    public String checkPhone(String mess){
        String phone;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            try{
                phone = sc.nextLine();
                if(!phone.matches("\\d{10}")) throw new PhoneException();
                else return phone;
            }
            catch(PhoneException ex){ /**/
                System.out.println(ex.getMessage());
            }
        } while(true);
    }

    public String checkEmail(String mess){
        String mail;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            try{
               mail = sc.nextLine();
                if(!mail.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) throw new EmailException();
                else return mail;
            }
            catch(EmailException ex){ /**/
                System.err.println(ex.getMessage());
            }
        } while(true);
    }

    public String checkString(String mess){
        String input;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            try{
                input = sc.nextLine();
                if(input!=null) return input;
                else System.out.println("Enter something");
            }
            catch(Exception ex){ /**/
                System.out.println(ex.getMessage());
            }
        } while(true);
    }

    public boolean checkAnswer(String mess){
        String fullName;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(mess);
            fullName = sc.nextLine().trim();
            fullName = fullName.replaceAll("\\s+"," ");
            return fullName.equals("Y")||fullName.equals("y");
        } while(true);
    }

    @Bean
    public CommandLineRunner Bai13Spring(EmployeeRepository employeeRepository, CertificateRepository certificateRepository) {
        return args -> {
            do{
                System.out.println("What do you want to do?");
                System.out.println("1. Find All");
                System.out.println("2. Add Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Update");
                System.out.println("5. Exit");
                int choice = checkInt(1, 5);
                switch (choice) {
                    case 1:
                            List<Employee> employees = employeeRepository.findAll();
                            if(employees.size()>0) employees.forEach(System.out::println);
                            else System.out.println("Nothing to show");
                        break;
                    case 2:
                        String fullName = checkName("Enter employee name");
                        Date birthDay = checkDate("Enter D.O.B ");
                        String phone = checkPhone("Enter phone number");
                        String email = checkEmail("Enter email");

                        Employee emp = new Employee(fullName, birthDay, phone, email);

                        System.out.println("Which type?");
                        System.out.println("0. Experience");
                        System.out.println("1. Fresher");
                        System.out.println("2. Intern");
                        int type = checkInt(0, 2);
                        switch (type) {
                            case 0:
                                System.out.println("Enter Year of Experience");
                                int ExpInYear = checkInt(0, Integer.MAX_VALUE);
                                String ProSkill = checkString("Enter ProSkill");

                                Experience exp = new Experience();
                                exp.setExpInYear(ExpInYear);
                                exp.setProSkill(ProSkill);

                                exp.setEmployee(emp);
                                emp.setExperience(exp);
                                break;
                            case 1:
                                Date Graduation_date = checkDate("Enter Graduation Date");
                                String Graduation_rank = checkString("Enter Graduation Rank");
                                String Education = checkString("Enter Education");

                                Fresher fre = new Fresher();

                                fre.setGraduation_date(Graduation_date);
                                fre.setGraduation_rank(Graduation_rank);
                                fre.setEducation(Education);

                                fre.setEmployee(emp);
                                emp.setFresher(fre);
                                break;
                            case 2:
                                String Majors = checkString("Enter Major");
                                System.out.println("Enter semester:");
                                int Semester = checkInt(0, 9);
                                String University_name = checkString("Enter University Name");

                                Intern ite = new Intern();
                                ite.setMajor(Majors);
                                ite.setSemester(Semester);
                                ite.setUniversity_name(University_name);

                                ite.setEmployee(emp);
                                emp.setIntern(ite);
                                break;
                            default:
                                break;
                        }

                        List<Certificate> certificates = new ArrayList<>();

                        while(checkAnswer("Do you have any certificate? (Y - yes/N - no)")){
                            Certificate cer = new Certificate();
                            cer.setCertificateName(checkString("Enter certificate name"));
                            cer.setCertificateRank(checkString("Enter certificate rank"));
                            cer.setCertificatedDate(checkDate("Enter certificated date"));

                            cer.setEmployee(emp);

                            certificates.add(cer);
                        }

                        if(certificates.size()>0){
                            emp.setCertificates(certificates);
                        }

                        employeeRepository.save(emp);

                        if(certificates.size()>0){
                            certificateRepository.saveAll(certificates);
                        }
                        break;
                    case 3:
                        System.out.println("Enter ID to be deleted:");
                        int Id = checkInt(1, Integer.MAX_VALUE);
                        Optional<Employee> e = employeeRepository.findById(Id);
                        if(e.isPresent()){
                            employeeRepository.delete(e.get());
                            System.out.println("Deleted");
                        }
                        else System.out.println("No such ID found!");
                break;
                    case 4:
                        System.out.println("Enter ID to be updated:");
                        Optional<Employee> update = employeeRepository.findById(checkInt(1, Integer.MAX_VALUE));
                        if(update.isPresent()){
                            Employee new_emp = update.get();

                            String upName = checkName("Enter new employee name");
                            Date upDay = checkDate("Enter new D.O.B ");
                            String upPhone = checkPhone("Enter new phone number");
                            String upMail = checkEmail("Enter new email");

                            new_emp.setFullName(upName);
                            new_emp.setBirthday(upDay);
                            new_emp.setPhone(upPhone);
                            new_emp.setEmail(upMail);

                            employeeRepository.save(new_emp);
                        }
                        break;
                    case 5:
                        System.exit(0);
                }
            } while(true);

/*            Employee emp = new Employee("Long", "2001-02-05", "0963487529", "vielongt52@gmail.com");

            Intern ite = new Intern();
            ite.setMajor("SE");
            ite.setSemester(5);
            ite.setUniversity_name("FPT");


            ite.setEmployee(emp);
            emp.setIntern(ite);


            List<Certificate> certificates = new ArrayList<>();
            certificates.add(new Certificate("Java", "Excellent", new SimpleDateFormat("dd/MM/yyyy").parse("05/02/2021")));
            certificates.add(new Certificate("C#", "Good", new SimpleDateFormat("dd/MM/yyyy").parse("05/02/2021")));

            certificates.forEach(c -> c.setEmployee(emp));
            emp.setCertificates(certificates);


            employeeRepository.save(emp);

            certificateRepository.saveAll(certificates);*/
        };

    }

}

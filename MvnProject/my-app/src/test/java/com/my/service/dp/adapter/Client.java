package com.my.service.dp.adapter;

// Example: java.io.OutputStreamWriter java.io.InputStreamReader are examples of object adapters.
public class Client {
  public static void main(String[] args) {
    EmployeeClassAdapter adapter = new EmployeeClassAdapter();
    //Two-way/ Using class adapter
    populateEmployee(adapter);
    BusinessCardDesigner designer = new BusinessCardDesigner();
    String card = designer.designCard(adapter);
    System.out.println(card);

    // Using Object Adaptor
    Employee employee = new Employee();
    populateEmployee(employee);
    EmployeeObjectAdapter employeeObjectAdapter = new EmployeeObjectAdapter(employee);
    designer.designCard(employeeObjectAdapter);
    System.out.println(card);

  }

  private static void populateEmployee(Employee employee) {
    employee.setFullName("Jack Will");
    employee.setJobTitle("QA");
    employee.setOfficeLocation("ZhangJia, Shanghai, China");
  }
}

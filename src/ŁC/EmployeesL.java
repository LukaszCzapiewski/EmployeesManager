package ŁC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class EmployeesL extends Employees implements java.io.Serializable {

    List<Employee> employeesList = new LinkedList<Employee>();
    ArrayList<Employee> employeesArrayList = new ArrayList<Employee>();
    private FaceContainer faceContainer;



    public FaceContainer getFaceContainer() {
        return faceContainer;
    }

    public void setFaceContainer(FaceContainer faceContainer) {
        this.faceContainer = faceContainer;
    }

    public EmployeesL(String name) {
        setInsurerName(name);

    }
    public List<Employee> getEmployeeList() {
        return employeesList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeesList = employeesList;
    }
}

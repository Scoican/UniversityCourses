package Repository.Student;

import Domain.Student;
import Repository.AbstractRepositories.XMLRepository;
import Validator.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StudentXMLRepository extends XMLRepository<String, Student> {

    public StudentXMLRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    /**
     *
     * @param document-the xml file
     * @param entity-the entity to be transformed
     * @return entity as Element to be put in the xml file
     */
    @Override
    public Element createElementFromEntity(Document document, Student entity) {
        Element e = document.createElement("student");
        e.setAttribute("studentId", entity.getID());

        Element name = document.createElement("name");
        name.setTextContent(entity.getName());
        e.appendChild(name);

        Element email = document.createElement("email");
        email.setTextContent(entity.getEmail());
        e.appendChild(email);

        Element teacher = document.createElement("teacher");
        teacher.setTextContent(entity.getTeacher());
        e.appendChild(teacher);

        Element group = document.createElement("group");
        group.setTextContent(entity.getGroup());
        e.appendChild(group);

        return e;
    }

    /**
     *
     * @param element to be transformed
     * @return the new entity created
     */
    @Override
    public Student createEntityFromElement(Element element) {
        String studentId = element.getAttribute("studentId");
        NodeList nods = element.getChildNodes();
        String name =element.getElementsByTagName("name")
                .item(0)
                .getTextContent();

        String email =element.getElementsByTagName("email")
                .item(0)
                .getTextContent();

        String teacher =element.getElementsByTagName("teacher")
                .item(0)
                .getTextContent();

        String group =element.getElementsByTagName("group")
                .item(0)
                .getTextContent();

        return new Student(studentId,name,email,teacher,group);
    }
}

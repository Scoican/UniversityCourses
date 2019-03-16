package Repository.Project;

import Domain.Project;
import Repository.AbstractRepositories.XMLRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import Validator.Validator;

public class ProjectXMLRepository extends XMLRepository<String,Project> {
    /**
     * Constructorul clasei
     *
     * @param fileName
     * @param validator - entitate Validator (validarea datelor)
     */
    public ProjectXMLRepository(String fileName, Validator<Project> validator) {
        super(fileName, validator);
    }

    /**
     *
     * @param document-the xml file
     * @param entity-the entity to be transformed
     * @return entity as Element to be put in the xml file
     */
    @Override
    public Element createElementFromEntity(Document document, Project entity) {
        Element e = document.createElement("project");
        e.setAttribute("projectId", entity.getID());

        Element desc = document.createElement("description");
        desc.setTextContent(entity.getDescription());
        e.appendChild(desc);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(entity.getDeadLine().toString());
        e.appendChild(deadline);

        Element currentWeek = document.createElement("currentWeek");
        currentWeek.setTextContent(entity.getCurrentWeek().toString());
        e.appendChild(currentWeek);

        Element startWeek = document.createElement("startWeek");
        startWeek.setTextContent(entity.getStartWeek().toString());
        e.appendChild(startWeek);


        return e;
    }

    /**
     *
     * @param element to be transformed
     * @return the new entity created
     */
    @Override
    public Project createEntityFromElement(Element element) {
        String projectId = element.getAttribute("projectId");
        NodeList nods = element.getChildNodes();
        String desc =element.getElementsByTagName("description")
                .item(0)
                .getTextContent();

        String deadline =element.getElementsByTagName("deadline")
                .item(0)
                .getTextContent();

        String currentWeek =element.getElementsByTagName("currentWeek")
                .item(0)
                .getTextContent();

        String startWeek =element.getElementsByTagName("startWeek")
                .item(0)
                .getTextContent();

        return new Project(projectId,desc,Integer.parseInt(deadline),Integer.parseInt(currentWeek),Integer.parseInt(startWeek));
    }
}

package Repository.Mark;

import Domain.Mark;
import Repository.AbstractRepositories.XMLRepository;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MarkXMLRepository extends XMLRepository<String, Mark> {

    /**
     * Constructor
     *
     * @param fileName - xml file
     * @param validator - Validator (data validation)
     */
    public MarkXMLRepository(String fileName, Validator<Mark> validator) {
        super(fileName, validator);
    }

    /**
     *
     * @param document-the xml file
     * @param entity-the entity to be transformed
     * @return entity as Element to be put in the xml file
     */
    @Override
    public Element createElementFromEntity(Document document, Mark entity){
        Element e = document.createElement("mark");
        e.setAttribute("markId",entity.getID());

        Element value = document.createElement("value");
        value.setTextContent(entity.getValue().toString());
        e.appendChild(value);

        Element feedback = document.createElement("feedback");
        feedback.setTextContent(entity.getFeedback());
        e.appendChild(feedback);

        return e;
    }

    /**
     *
     * @param element to be transformed
     * @return the new entity created
     */
    @Override
    public Mark createEntityFromElement(Element element) {
        String markId = element.getAttribute("markId");
        NodeList node = element.getChildNodes();

        String value =element.getElementsByTagName("value")
                .item(0)
                .getTextContent();
        String feedback=element.getElementsByTagName("feedback")
                .item(0)
                .getTextContent();
        String[] split=markId.split("-");
        return new Mark(split[0],split[1],Float.parseFloat(value),feedback);

    }
}

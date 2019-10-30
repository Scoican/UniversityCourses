package Repository.AbstractRepositories;

import Domain.HasID;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public abstract class XMLRepository <ID, E extends HasID<ID>> extends  InMemoryRepository<ID,E>{
    private String fileName;

    /**
     * Constructorul clasei
     *
     * @param validator - entitate Validator (validarea datelor)
     */
    public XMLRepository(String fileName,Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    /**
     * Method that reads and loads all the data from a file,creating entities
     */
    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i < children.getLength(); i++) {
                Node entityElement = children.item(i);
                if(entityElement instanceof Element) {
                    E entity = createEntityFromElement((Element)entityElement);
                    super.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that writes all entities to the file
     */
    private void writeToFile(){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root  = document.createElement("inbox");
            document.appendChild(root);
            super.findAll().forEach(e->{
                Element elem = createElementFromEntity(document,e);
                root.appendChild(elem);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(fileName));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Abstract method that creates an xml data element out of a string from the file
     * @param document
     * the file
     * @param entity
     * the transformed entity
     * @return the new Entity as xml data
     */
    public abstract Element createElementFromEntity(Document document, E entity);

    /**
     * Abstract method that creates an entity out of a xml node
     * @param element
     * the entity to be created
     * @return the new Entity
     */
    public abstract E createEntityFromElement(Element element);

    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    @Override
    public E save(E entity) {
        E stuff = super.save(entity);
        if (stuff==null){
            writeToFile();
        }
        return stuff;
    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    @Override
    public E delete(ID id) {
        E temp=super.delete(id);
        if(temp!=null)
            writeToFile();
        return temp;
    }

    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     */
    @Override
    public E update(E entity) {
        E temp=super.update(entity);
        writeToFile();
        return temp;
    }

}
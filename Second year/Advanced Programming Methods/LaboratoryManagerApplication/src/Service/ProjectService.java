package Service;

import Domain.Project;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Project.ProjectXMLRepository;
import Utils.Observable;
import Utils.Observer;
import Utils.ProjectChangeEvent;
import Validator.ProjectException;
import Validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectService implements Observable<ProjectChangeEvent> {

    private String fileNameProject;
    private CrudRepository<String, Project> projects;
    private List<Observer<ProjectChangeEvent>> observers=new ArrayList<>();

    public ProjectService(String fileNameProject) {
        this.fileNameProject=fileNameProject;
        this.projects = new ProjectXMLRepository(fileNameProject,new ProjectException());
    }

    /**
     * Method that adds a new project
     * @param id-string
     * id must not be null
     * @param description-string
     * @param deadLine-Integer
     * @param currentWeek-Integer
     * @param startWeek-Integer
     * @return null- if the given Project is saved
     * otherwise returns the Project (id already exists)
     * @throws ValidatorException
     * if the Project is not valid
     * @throws IllegalArgumentException
     * if the given Project is null. *
     */
    public Project addProject(String id,String description,Integer deadLine,Integer currentWeek,Integer startWeek){
        return projects.save(new Project(id,description,deadLine,currentWeek,startWeek));
    }

    /**
     *
     * @param id -the id of the Project to be returned
     * id must not be null
     * @return the Project with the specified id
     * or null - if there is no Project with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    public Project findProject(String id){
        return projects.findOne(id);
    }

    /**
     * Method that returns the number of Projects
     * @return the number of projects
     */
    public AtomicInteger sizeProject(){
        AtomicInteger size= new AtomicInteger();
        Iterable<Project> list= getProjects();
        list.forEach(x-> size.getAndIncrement());
        return size;
    }

    public Iterable<Project> getProjects(){
        return projects.findAll();
    }

    public Project deleteProject(String id){
        return projects.delete(id);
    }

    /**
     * Method that extends the deadline of a project with a given number of weeks
     * @param id
     * id must not be null
     * @param nrOfWeeks-number of weeks to extend with
     * @return true if the extension was successful
     *         false otherwise
     */
    public boolean extendDeadLine(String id,Integer nrOfWeeks){
        Project project=projects.findOne(id);
        if(project!=null) {
            if (project.getCurrentWeek() <= project.getDeadLine()) {
                if (project.getDeadLine() + nrOfWeeks <= 14) {
                    project.setDeadLine(project.getDeadLine() + nrOfWeeks);
                    projects.update(project);
                    return true;
                } else return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public void addObserver(Observer<ProjectChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ProjectChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ProjectChangeEvent projectChangeEvent) {
        observers.forEach(x->x.update(projectChangeEvent));
    }
}

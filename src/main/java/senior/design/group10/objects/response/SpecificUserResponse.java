package senior.design.group10.objects.response;

public class SpecificUserResponse {
    private boolean found;
    private String name;
    private String dept;
    private String occurrences;
    private String lastEntered;
    private String creationDate;

    public SpecificUserResponse(boolean isFound){
        this.found = isFound;
    }

    public SpecificUserResponse(boolean found, String name, String dept, String occurrences, String lastEntered, String creationDate) {
        this.found = found;
        this.name = name;
        this.dept = dept;
        this.occurrences = occurrences;
        this.lastEntered = lastEntered;
        this.creationDate = creationDate;
    }

    public boolean isFound() {
        return found;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getOccurrences() {
        return occurrences;
    }

    public String getLastEntered() {
        return lastEntered;
    }

    public String getCreationDate() {
        return creationDate;
    }
}

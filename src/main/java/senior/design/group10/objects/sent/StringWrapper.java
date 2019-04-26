package senior.design.group10.objects.sent;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

public class StringWrapper {

    private String string;

    public StringWrapper(){}

    public StringWrapper(String string){
        this.string = string;
    }

    public String getString(){
        return string;
    }
}

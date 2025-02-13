package org.delaware.tools.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DBItem implements Serializable {
    private int id;
    private String identifier;
    private List<String> lore = new ArrayList<> ( );
    private String name;
    private Attribute attribute = new Attribute (  );
    private String type;

    @Override
    public boolean equals ( Object object ) {
        if (this == object) return true;
        if (!(object instanceof DBItem)) return false;
        DBItem dbItem = (DBItem) object;
        return getId ( ) == dbItem.getId ( ) && Objects.equals ( getLore ( ), dbItem.getLore ( ) ) && Objects.equals ( getName ( ), dbItem.getName ( ) );
    }

    @Override
    public int hashCode () {
        return Objects.hash ( getId ( ), getLore ( ), getName ( ) );
    }
}

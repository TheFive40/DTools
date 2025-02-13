package org.delaware.tools.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TP implements Serializable {
    private int id;
    private int value;
    private String displayName;
    private ArrayList<String> lore = new ArrayList<> ( );

    public TP setLore ( ArrayList<String> lore ) {
        this.lore = lore;
        return this;
    }

    public TP setDisplayName ( String displayName ) {
        this.displayName = displayName;
        return this;
    }

    public TP setValue ( int value ) {
        this.value = value;
        return this;
    }

    public TP setId ( int id ) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals ( Object object ) {
        if (this == object) return true;
        if (!(object instanceof TP)) return false;
        TP tp = (TP) object;
        return getId ( ) == tp.getId ( ) && getValue ( ) == tp.getValue ( ) && Objects.equals ( getDisplayName ( ), tp.getDisplayName ( ) ) && Objects.equals ( getLore ( ), tp.getLore ( ) );
    }

    @Override
    public int hashCode () {
        return Objects.hash ( getId ( ), getDisplayName ( ), getLore ( ) );
    }
}

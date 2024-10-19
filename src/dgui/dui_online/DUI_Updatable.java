// Package declaration
package dgui.dui_online;

// Import
import java.awt.event.ActionListener;


/**
 * Interface UI Updatable
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This interface allow UI to have an updater which update some informations like
 * player list
 */
public interface DUI_Updatable extends ActionListener{
    public void updatableAction();
}

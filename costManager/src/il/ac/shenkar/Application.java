package il.ac.shenkar;

import il.ac.shenkar.model.*;
import il.ac.shenkar.view.*;
import il.ac.shenkar.viewmodel.*;

public class Application {
    public static void main(String args[]){

        //creating the application components
        try {
            // creating I-il.ac.shenkar.model object
            IModel model = new Model();
            //creating il.ac.shenkar.view object
            IView view = new View();
            //creating viewModel object
            IViewModel vm = new ViewModel();

            //connecting the components with each other
            view.setViewModel(vm);
            vm.setModel(model);
            vm.setView(view);

        } catch (CostManagerException e) {
            e.printStackTrace();
        }


    }
}

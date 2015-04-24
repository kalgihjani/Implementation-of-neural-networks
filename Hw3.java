
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Hw3 {

    public static void main(String[] args) throws IOException {
        
        if(args.length != 4)    //System should accept only 4 arguments:
        {                       //training file name, test file name, learning rate, number of iterations
            System.out.println("You must provide exactly 4 arguments");
            System.exit(1);
        }
        
        
        String train_file=args[0];          //taking command line arguments into variables
        String test_file=args[1];
        double rate=Double.parseDouble(args[2]);
        int iteration=Integer.parseInt(args[3]);
        
        
        double threshhold=0.5;              //setting up threshhold to classify the data
        int iteration_count;                // will keep count of iteration
        
        FileReader fr=new FileReader(new File(train_file));     //file readers for training and testing files
        FileReader fr_test=new FileReader(new File(test_file));

        BufferedReader br=new BufferedReader(fr);
        BufferedReader br_test=new BufferedReader(fr_test);
        
        String s;
        String s_test;
        
        br_test.readLine();         //reading first line which has attribute names only
        
        //reading first line and splitting it by tab to know how many attributes are there.
        //then creating weight array of same size as of number of attributes
        //initialized all weights to 0
        String first_row=br.readLine();
        String[] firstrow=first_row.split("\t");
        double[] weight=new double[firstrow.length];
        for(int k=0;k<weight.length;k++)
        {
            weight[k]=0;
        }
        
        //creating arraylist to store records
        //here each record will be stored as integer array and than it will be added to Arraylist of records
        ArrayList<int[]> records=new ArrayList<int[]>();
        ArrayList<int[]> records_test=new ArrayList<int[]>();
        
        while((s=br.readLine()) != null)        //stop at the end of file
        {   
            if(s.length()==0)           //avaoiding blank lines
            {
                continue;
            }
            String[] temp = s.split("\t");      //splitting attributes by tab and storing into String array
            int[] attrb=new int[temp.length];
            
            for(int k=0;k<temp.length;k++)      //converting string array to integer array
            {
                attrb[k]=(Integer.parseInt(temp[k]));
            }
            records.add(attrb);                 //array i.e here record is added to arraylist
        }
        
        while((s_test=br_test.readLine()) != null)    //same loop as above for the test file
        {   
            if(s_test.length()==0)
            {
                continue;
            }
            String[] temp = s_test.split("\t");
            int[] attrb=new int[temp.length];
            
            for(int k=0;k<temp.length;k++)
            {
                attrb[k]=(Integer.parseInt(temp[k]));
            }
            records_test.add(attrb);
        }
        
        //iteration_count will be assigned maximum iterations value provided by user.
        //than it will be decresed by 1 on each iteration.
        //when it will become zero, loop will be stopped.
        iteration_count=iteration;
        while(iteration_count != 0)
        {   
        for(int[] temp1:records)    //iterating each row
        {
            int sum=0;
            int k;
            for(k=0;k<weight.length;k++)    // calculating summation of (weight*attribute value) for each row
            {
                sum+=(weight[k]*temp1[k]);
            }
            double out=1/(1+Math.exp(-sum));    //calculating sigmoid function
            int target_output=temp1[k];
            //now we will find how much each weight needs to be updated.
            //equation for that is weight=weight+learning_rate*(t-o)*o*(o-1)*X
            //in this equation, exept weight and X, everything else is same for one record
            //So we will first calculate it and than update each weight corresponding to its attribute
            double error=rate*(target_output-out)*(out*(1-out));    
            for(k=0;k<weight.length;k++)
            {
                double update_weight=error*temp1[k];
                weight[k]+=update_weight;
                
            }
            iteration_count--;         //decreasing iteration count on each iteration
            if(iteration_count==0)     //stopping the loop when counter hits zero
            {
                break;
            }
        }
        
        }
        
        //training is done. Now we will test the training file
        
        double correct=0;
        double wrong=0;
        for(int[] train:records)
        {
            int output;
            int q;
            double sum1=0;
            for(q=0;q<weight.length;q++)    //preaparing the input as above
            {
                sum1+=(weight[q]*train[q]);
                 
            }
            double out=1/(1+Math.exp(-sum1));   //calculating sigmoid function
            if(out>=threshhold)            //comparing output with threshhols
            {
                output=1;                  //if output greater than threshhold, classifying it as 1
            }
            else
            {
                output=0;                   //otherwise classifying it as 0
                
            }
            if(output==train[q])          //counting the correct outputs by comparing it to target output
            {
                correct++;
            }
            else                         //counting the wrong outputs by comparing it to target output
            {
                wrong++;
            }
            
        }
        
        //counting accuracy by calculating percentage of intances that were correctly classified
        double accuracy_train=(correct/(correct+wrong))*100;    
        //printing the accuaracy of training file
        System.out.println("Accuracy on training set("+(correct+wrong)+" instances): "+accuracy_train+"%");
        
        //now we will test the testing file
        correct=0;wrong=0;
        for(int[] train:records_test)
        {
            
            int output;
            int q;
            double sum1=0;
            for(q=0;q<weight.length;q++)    //preaparing the input as above
            {
                sum1+=(weight[q]*train[q]);
                 
            }
            double out=1/(1+Math.exp(-sum1));   //calculating sigmoid function
            if(out>=threshhold)                 //comparing output with threshhols
            {
                output=1;                   //if output greater than threshhold, classifying it as 1
            }
            else
            {
                output=0;                   //otherwise classifying it as 0
            }
            if(output==train[q])         //counting the correct outputs by comparing it to target output
            {
                correct++;
            }
            else                        //counting the wrong outputs by comparing it to target output
            {
                wrong++;
            }
           
        }
        
        //counting accuracy by calculating percentage of intances that were correctly classified
        double accuracy_test=correct/(correct+wrong)*100;
        //printing the accuaracy of training file
        System.out.println("Accuracy on testing set("+(correct+wrong)+" instances): "+accuracy_test+"%");
    }
    
}


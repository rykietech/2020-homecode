// @rykliefh
// Name: Hishaam Ryklief
// @3652426
// csc212
// practical 1 semester 2 term4

import java.util.*;
import java.io.*;
public class practical1 {

    //create class school
    private class school{
        String name="";
        double geo_cor=0.0d;
		double geo_cor2=0.0d;
        double rating=0.0d;


        //Constructor school
        public school(String name,double geo_cor1,double geo_cor2,double rating){
            this.name=name;
            this.geo_cor=geo_cor1;
			this.geo_cor2=geo_cor2;
            this.rating=rating;
        }

        //creating setters
        public void setName(String name){
            this.name = name;
        }

        public void setGeo_cor(double geo_cor){
            this.geo_cor=geo_cor;
        }
		
		public void setGeo_cor2(double geo_cor2){
            this.geo_cor2=geo_cor2;
        }

        public void setRating(double rating){
            this.rating=rating;
        }

        //creating getters
        public String getName(){
            return this.name;
        }

        public double getGeo_cor(){
            return this.geo_cor;
        }
		public double getGeo_cor2(){
            return this.geo_cor2;
        }

        public double getRating(){
            return this.rating;
        }

        @Override
        public String toString(){
            return this.name+", "+this.geo_cor+", " +this.geo_cor2+", "+this.rating ;
        }
    }
    school[] schoolArrayList;
    ArrayList<school> schoolvar = new ArrayList<school> ();//fixes null exception error

    /*
     *
     * @ Question(i) reads csv file into an array seperated by semi colons
     *
     *
     */

    public school[] READ_FILE(){
        File file_csv = new File ("Soweto.csv");

        try{
            BufferedReader buff_reader = new BufferedReader (new FileReader (file_csv));
            buff_reader.readLine ();
            String st;
            while((st=buff_reader.readLine ())!=null){
                String[] value = st.split (";");  //collects data between ;
				double value1 =Double.parseDouble (value[ 1 ]); // converts location1 to double
				double value2 =Double.parseDouble (value[ 2 ]); // converts location2 to double
				double value3 =Double.parseDouble (value[ 3 ]); // converts rating to double
              schoolvar.add( new school (value[0],value1,value2,value3));
            }
        }
        catch (IOException e){
            e.printStackTrace ();
        }
        schoolArrayList=schoolvar.toArray (new school[0]);      //converting from arraylist to array
        return schoolArrayList;
    }
    /*
     *       @ Question II Creating iterative merge sort
     *
     */
    public void MERGE( school[] array, int low, int mid, int  high){
        int arr_size1 = mid-low+1;
        int arr_size2 = high-mid;
        int i;
        int j;
        int k;
                /*
                Creating a  temp array for low and high
                 */

        school[] LOW = new school[arr_size1];		//bottom half
        school[] HIGH = new school[arr_size2];		//top half
        for (i =0;i<arr_size1;i++){
            LOW[i]=array[low+i];
        }

        for (j=0;j<arr_size2;j++){
            HIGH[j]=array[mid+1+j];
        }

        i=j=0;
        k=low;
        while(i<arr_size1 &&j<arr_size2)
        {
            if(LOW[i].name.compareToIgnoreCase (HIGH[j].name)<0)
            {
                array[k]=LOW[i];
                i++;
            }
            else{
                array[k]=HIGH[j];
                j++;
            }
            k++;
        }
        while (i<arr_size1){
            array[k]=LOW[i];
            i++;
            k++;
        }
        while(j<arr_size2){
            array[k]=HIGH[j];
            j++;
            k++;
        }

    }
    //iterative merge sort
    public void MERGE_SORT( school[] array, int n){
        int size_sub;	//sub array size
        int low_s;		// size of low 
        for (size_sub=1;size_sub<=n-1;size_sub=2*size_sub){
            for (low_s=0;low_s<n-1;low_s+=2*size_sub){
                int mid_index = Math.min (low_s+size_sub-1,n-1);
                int high=Math.min(low_s+2*size_sub-1,n-1);
                MERGE(array,low_s,mid_index,high);
            }
        }
    }




    /*          QUICKSORT
     *       @ Question III Creating iterative quick sort
     *          quicksort - partition * pivot*
     */
				//swap method that swaps arr[i] and array[j]
    public void swap( school arr[], int i, int j ) {
        school temp = arr[ i ];
        arr[ i ] = arr[ j ];
        arr[ j ] = temp;
    }

    public int partition( school arr[], int l, int h ) {
        school x = arr[ h/2 ];		
        int i = (l - 1);
        for (int j = l; j <= h - 1; j++) {
            if (arr[ j ].name.compareToIgnoreCase (x.name) < 0) {
                i++;
                swap (arr, i, j);
            }
        }
        swap (arr, i + 1, h);
        return (i + 1);
    }
    //Iterative quick sort
    public void quick_sort( school arr[], int l, int h ) {
        int store[] = new int[ h - l + 1 ];       //creating an auxialry storage array for index
        int top = -1;
        store[ ++top ] = l;
        store[ ++top ] = h;
        while (top >= 0) {
            h = store[ top-- ];             //pop top element on storage array
            l = store[ top-- ];
            int p = partition (arr, l, h);      //call function partition to find pivot index
            if (p - 1 > l) {                    //checks pivot index -1 is less than the low index
                store[ ++top ] = l;             
                store[ ++top ] = p - 1;         
            }
            if (p + 1 < h) {
                store[ ++top ] = p + 1;
                store[ ++top ] = h;
            }
        }
    }
    /**
     *  QUESTION III creating function to find elapsed time for both merge and quick sort and write to file with sorts
     */
    public void RUN() {                 //function run which calls the merge_sort and quick_sort and writes to file
        long start_time = System.currentTimeMillis ();      //start time of quick sort
        quick_sort (schoolArrayList, 0, schoolArrayList.length - 1);
        long end_time = System.currentTimeMillis ();        //end time of quick sort

        long start_time1 = System.currentTimeMillis ();     //start time of merge sort
        MERGE_SORT (schoolArrayList,schoolArrayList.length);
        long end_time1 = System.currentTimeMillis ();       //end time of quick sort
        try{
        FileWriter write_to_file = new FileWriter ("Output.txt");
        write_to_file.write ("time in miliseconds: "+(end_time1-start_time1)+"ms\n");       //merge sort elapsed time
            write_to_file.write ("[");
        for (int i = 0; i < schoolArrayList.length; i++){
            write_to_file.write(schoolArrayList[i]+",");
        }
            write_to_file.write ("]");
            write_to_file.write ("\n\ntime in miliseconds: "+(end_time-start_time)+"ms\n"); //Quicksort elapsed time
            write_to_file.write ("[");
            for (int j= 0; j < schoolArrayList.length; j++){
                write_to_file.write(schoolArrayList[j]+",");
            }
            write_to_file.write ("]");
            write_to_file.close ();
        }catch (IOException e){
            System.out.println ("Error");
            e.printStackTrace ();
        }
    }




    public static void main(String[] args)throws Exception{
     practical1 test = new practical1 ();
     test.READ_FILE ();
     test.RUN ();

    }
}

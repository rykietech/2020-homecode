// Practical 2 semester 2 term 4 csc212
// Name: Hishaam Ryklief
// Stuedent number: 3652426

import java.io.*;
import java.util.*;


public class practical2 {
        

    //create class school
    private class school{
        String name="";

        double rating=0.0d;
        int school_num = 0;


        //Constructor school
        public school(String name,double rating, int school_Num){
            this.name=name;
            this.rating=rating;
            this.school_num=school_Num;
        }

        //creating setters
        public void setName(String name){
            this.name = name;
        }



        public void setRating(double rating){
            this.rating=rating;
        }

        public void setSchool_num(int school_num){
            this.school_num=school_num;
        }

        //creating getters
        public String getName(){
            return this.name;
        }



        public double getRating(){
            return this.rating;
        }

        public int getSchool_num(){
            return this.school_num;
        }

        @Override
        public String toString(){
            return this.name+", "+this.rating +", "+this.school_num ;
        }
    }
    int[] school_num;
    int[] school_num2;
    school[] schoolArrayList;
    ArrayList<school> schoolvar = new ArrayList<school> ();//fixes null exception error
    private class Graph{
        int Number_of_vertices;  //No of vertices
        LinkedList<Integer> adjacency_list[];
        boolean visited[];

        public Graph(int v){
            Number_of_vertices = v;
            adjacency_list = new LinkedList[v];
            visited = new boolean[Number_of_vertices];
            for (int i = 0; i <v ; i++) {
                adjacency_list[i]=new LinkedList ();
            }
        }

        public void add_Edge(int v,int w){
            adjacency_list[v].add (w);
        }

        public void BFS(int source){
            boolean[] visited = new boolean[Number_of_vertices];
            LinkedList<Integer> queue = new LinkedList<Integer> ();
            visited[source]=true;
            queue.add(source);
            while (queue.size ()!=0){
                source = queue.poll ();
                school_num[source]=source;
                Iterator<Integer> iter = adjacency_list[source].listIterator ();
                while (iter.hasNext ()){
                    int n = iter.next ();
                    if(!visited[n]){
                        visited[n]=true;
                        queue.add (n);
                    }
                }
            }
        }

        /*
         *
         *           QUESTION II) DFS
         */
 
        // A function used by DFS
        public void DFSUtil(int v,boolean visited[])
        {
            // Mark the current node as visited and print it
            visited[v] = true;
            school_num2[v]=v;
            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i = adjacency_list[v].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                    DFSUtil(n, visited);
            }
        }

        // The function to do DFS traversal. It uses recursive DFSUtil()
        public void DFS(int v)
        {
            // Mark all the vertices as not visited(set as
            // false by default in java)
            boolean visited[] = new boolean[Number_of_vertices];

            // Call the recursive helper function to print DFS traversal
            DFSUtil(v, visited);
        }

    }



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
            int count =0;
            while((st=buff_reader.readLine ())!=null){
                String[] value = st.split (";");  //collects data between ;
                double value1 =Double.parseDouble (value[ 1 ]); // converts location1 to double
                double value2 =Double.parseDouble (value[ 2 ]); // converts location2 to double
                double value3 =Double.parseDouble (value[ 3 ]); // converts rating to double
                    if(value3==5.0){
                    schoolvar.add (new school(value[0],value3,count++));
                    }
            }

        }
        catch (IOException e){
            e.printStackTrace ();
        }
        schoolArrayList=schoolvar.toArray (new school[0]);      //converting from arraylist to array
        for (int i = 0; i <schoolArrayList.length; i++) {
            schoolArrayList[i].setSchool_num (i);
        }
        return schoolArrayList;
    }

    public void RUN2(school[] a,int source){
        Graph test = new Graph (a.length);
        school_num = new int[a.length];
        school_num2 = new int[a.length];
       

        for (int i = 0; i <a.length ; i++) {
            for (int j = 0; j <a.length ; j++) {
                if (i!=j){
               test.add_Edge (a[i].school_num,a[j].school_num);
                }
            }
        }
        test.BFS (source);
        System.out.println();
        test.DFS (source);

    }

    public void MERGE(school[] array, int low, int mid, int  high){
        int arr_size1 = mid-low+1;
        int arr_size2 = high-mid;
        int i;
        int j;
        int k;
                /*
                Creating a  temp array for low and high
                 */

        school[] LOW = new school[arr_size1];
        school[] HIGH = new school[arr_size2];
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
        int size_sub;
        int left_s;
        for (size_sub=1;size_sub<=n-1;size_sub=2*size_sub){
            for (left_s=0;left_s<n-1;left_s+=2*size_sub){
                int mid_index = Math.min (left_s+size_sub-1,n-1);
                int right=Math.min(left_s+2*size_sub-1,n-1);
                MERGE(array,left_s,mid_index,right);
            }
        }
    }

    public void Write_toOutput(){

        try{
            FileWriter write_to_file = new FileWriter ("Output_practical2.txt");
            write_to_file.write ("Values [");
            for (int i = 0; i < schoolArrayList.length; i++){
                write_to_file.write(schoolArrayList[i]+",");
            }
            write_to_file.write ("]\n");
            MERGE_SORT (schoolArrayList,schoolArrayList.length);
            write_to_file.write ("Sorted [");
            for (int j= 0; j < schoolArrayList.length; j++){
                write_to_file.write(schoolArrayList[j]+",");
            }
            write_to_file.write ("]\n\n\n");
            write_to_file.write("Following in Depth First Traversal at vertex 0\n");
            RUN2 (schoolArrayList,0);
            for (int j= 0; j < school_num2.length; j++){
                write_to_file.write(school_num2[j]+" ");
            }
            write_to_file.write("\n\n\nFollowing Breadth First Traversal at vertex 0\n");
            for (int j= 0; j < school_num.length; j++){
                write_to_file.write(school_num[j]+" ");
            }
            write_to_file.close ();
        }catch (IOException e){
            System.out.println ("Error");
            e.printStackTrace ();
        }
    }
    public static  void main(String[] args) throws Exception{
        practical2 test= new practical2 ();
        test.READ_FILE ();
     test.Write_toOutput ();
        System.out.print ("\n");

    }
}

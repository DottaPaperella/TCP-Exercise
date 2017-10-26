//No space only tab
package tcp;

/**
 *
 * @author djsdvg
 */
public class TCP {
    int numSegments;
    int byt;
    int mSS;
    int cWND;
    int sSTHRESH;
    int []grafico;
    int totSeg;
    int rCVWND;
    
    
    /**
     * 
     * @param byt byte that must be send through TCP
     * @param mSS segments' size
     * @param rCVWND windows' max size
     */
    public TCP( int byt, int mSS, int rCVWND) {
        this.numSegments = byt/mSS;
        this.byt = byt;
        this.mSS = mSS;
        this.cWND = 1;
        this.rCVWND=rCVWND/mSS;
        this.sSTHRESH = rCVWND/(2*mSS);
        grafico=new int[100];
        totSeg=0;
    }
    
    /**
     * 
     * @param byt byte that must be send through TCP
     * @param mSS segments' size
     * @param rCVWND windows' max size
     * @param sSTHRESH  SSTHRESH size
     */
    public TCP( int byt, int mSS, int rCVWND,int sSTHRESH) {
        
        this.numSegments = byt/mSS;
        if(byt%mSS!=0)
            this.numSegments ++;    
        this.byt = byt;
        this.mSS = mSS;
        this.cWND = 1;
        this.rCVWND=rCVWND/mSS;
        this.sSTHRESH = sSTHRESH;
        grafico=new int[100];
        totSeg=0;
    }
    
    /**
     * fill an array with the solution of the exercise
     */
    public void draw(){
        int posDown=0;
        int posGraf=0;
        
        while(totSeg<numSegments){
            
                totSeg+=cWND;
                grafico[posGraf]=cWND;
                posGraf++;
                if(cWND<sSTHRESH)
                    cWND=Integer.min(cWND*2,sSTHRESH);
                else
                    cWND=Integer.min(cWND+1,rCVWND);
            
            
            
        }
        
    }
    
    
    /**
     * fill an array with the solution of the exercise
     * @param down array that contain the time when the network is offline
     */
    public void draw(int down[]){
        int posDown=0;
        int posGraf=0;
        int failCons=0;
        
        while(totSeg<numSegments){
            if(down[posDown]!=posGraf){
                failCons=0;
                totSeg+=cWND;
                grafico[posGraf]=cWND;
                posGraf++;
                if(cWND<sSTHRESH)
                    cWND=Integer.min(cWND*2,sSTHRESH);
                else
                    cWND=Integer.min(cWND+1,rCVWND);
            }
            else{
                failCons++;
                for(int k=0;k<(failCons*2);k++){
                    grafico[posGraf]=cWND-2*cWND;
                    posGraf++;  
                    if(down[posDown]<=posGraf)
                        posDown=Integer.min(posDown+1,down.length-1);

                }
                sSTHRESH=Integer.max(cWND/2,2);
                cWND=1;
            }
            
        }
    }
    
    @Override
    /**
     * Print the array with the solution of the exercise
     */
    public String toString(){
        String toReturn="WindowSize: ";
        for(int i=0;grafico[i]!=0;i++){
            toReturn+=String.format("%02d",grafico[i])+" ";
        }
        toReturn+="\n         t: ";
        for(int i=0;grafico[i]!=0;i++)
        	if(grafico[i]>0)
        		toReturn+=String.format("%02d",i)+" ";
        	else
        		toReturn+=" "+String.format("%02d",i)+" ";
        	
        return toReturn;
    }
}

package sample;


import java.io.*;

public class Trace implements Serializable {

    private Calcul calcul;
    public static String fileName = "trace.txt";

    public Trace(Calcul calcul)
    {
        this.calcul=calcul;
    }

    public Calcul getCalcul()
    {
        return this.calcul;
    }

    public void SetAll(char op, int opg, int opd, int res)
    {
        getCalcul().setOp(op);
        getCalcul().setOpg(opg);
        getCalcul().setOpd(opd);
        getCalcul().setRes(res);
        getCalcul().setTrace(this);
    }

    public void savop(char op, int opg, int opd, int res)
    {
        SetAll(op,opg,opd,res);
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(Trace.fileName));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(getCalcul());
            objectOut.close();
            fileOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Calcul lireOp()
    {
        Calcul calcul = null;
        try {
            FileInputStream fileInput = new FileInputStream(new File(Trace.fileName));
            ObjectInputStream oi = new ObjectInputStream(fileInput);
            calcul = (Calcul) oi.readObject();
            oi.close();
            fileInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return calcul;
    }
}

package sample;
public  class FilterC extends Filter {
 
    Pipe _dataINPipe;
    Pipe _dataOUTPipe;
     
    public FilterC(Pipe _dataINPipe, Pipe _dataOUTPipe) {
		super();
		this._dataINPipe = _dataINPipe;
		this._dataOUTPipe = _dataOUTPipe;
	}
    
    public String getData(){
        return _dataINPipe.dataOUT();
    }
     
    public void sendData( String tempData){
        _dataOUTPipe.dataIN(tempData);
    }

    @Override
	public void run() {
		// TODO Auto-generated method stub
		execute();
	}
	@Override
	synchronized void execute() {
		// TODO Auto-generated method stub
		Calcul calcul= new Calcul();
		Calcul calcul1;
		Trace trace = new Trace(calcul);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		calcul1=trace.lireOp();
		char op=calcul1.getOp();
		int number1 = calcul1.getOpg();
		int number2 = calcul1.getOpd();
		int result = calcul1.getRes();
		String strOut= ""+op+"-"+number1+"-"+number2+"-"+result;
		_dataOUTPipe.dataIN(strOut);
		System.out.println("filter C : lecture du fichier Trace");
	}
}
 
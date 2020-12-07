package Client;

public class Tranzactie {
	private String dataTranzactie;
	private String oraTranzactie;
	private String tipTranzactie;
	private String sumTranzactie;

	public Tranzactie(String dataTranzactie, String oraTranzactie, String tipTranzactie, String sumTranzactie) {
		super();
		this.dataTranzactie = dataTranzactie;
		this.oraTranzactie = oraTranzactie;
		this.tipTranzactie = tipTranzactie;
		this.sumTranzactie = sumTranzactie;
	}

	public String getDataTranzactie() {
		return dataTranzactie;
	}

	public void setDataTranzactie(String dataTranzactie) {
		this.dataTranzactie = dataTranzactie;
	}

	public String getOraTranzactie() {
		return oraTranzactie;
	}

	public void setOraTranzactie(String oraTranzactie) {
		this.oraTranzactie = oraTranzactie;
	}

	public String getTipTranzactie() {
		return tipTranzactie;
	}

	public void setTipTranzactie(String tipTranzactie) {
		this.tipTranzactie = tipTranzactie;
	}

	public String getSumTranzactie() {
		return sumTranzactie;
	}

	public void setSumTranzactie(String sumTranzactie) {
		this.sumTranzactie = sumTranzactie;
	}

	@Override
	public String toString() {
		return "Tranzactie [dataTranzactie=" + dataTranzactie + ", oraTranzactie=" + oraTranzactie + ", tipTranzactie="
				+ tipTranzactie + ", sumTranzactie=" + sumTranzactie + "]";
	}
	
	

}

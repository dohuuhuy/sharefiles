package FileMoi;

public class Files {
	private int id;
	private String tieuDe;
	private String nguoiGui;

	public Files(int id, String tieuDe, String nguoiGui) {
		this.id = id;
		this.tieuDe = tieuDe;
		this.nguoiGui = nguoiGui;

	}

	public Files() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTieuDe() {
		System.out.println("123");
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	public String getNguoiGui() {
		System.out.println("456");
		return nguoiGui;
	}

	public void setNguoiGui(String nguoiGui) {
		this.nguoiGui = nguoiGui;
	}

}
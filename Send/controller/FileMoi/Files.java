package FileMoi;

import javafx.scene.control.CheckBox;

public class Files {
	private int id;
	private String tieuDe;
	private String nguoiGui;
	private CheckBox select;

	public Files(int id, String tieuDe, String nguoiGui) {
		this.id = id;
		this.tieuDe = tieuDe;
		this.nguoiGui = nguoiGui;
		this.select = new CheckBox();

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

		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	public String getNguoiGui() {

		return nguoiGui;
	}

	public void setNguoiGui(String nguoiGui) {
		this.nguoiGui = nguoiGui;
	}
 
	public CheckBox getSelect() {

		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}
}
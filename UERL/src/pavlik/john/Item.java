package pavlik.john;

public class Item implements Comparable<Item> {

	String lin;
	String sublin;
	String nomenclature;
	Long required, authorized, onhand, duein, shortage;
	String rating;
	String remarks;
	String docNum;

	public Item(String lin, String sublin, String nomenclature,
			long required2, long authorized2, long onhand2,
			long duein2, long shortage2, String rating, String remarks,
			String docNum) {
		super();
		this.lin = lin;
		this.sublin = sublin;
		this.nomenclature = nomenclature;
		this.required = required2;
		this.authorized = authorized2;
		this.onhand = onhand2;
		this.duein = duein2;
		this.shortage = shortage2;
		this.rating = rating;
		this.remarks = remarks;
		this.docNum = docNum;
	}

	public String getLin() {
		return lin;
	}

	public String getSublin() {
		return sublin;
	}

	public String getNomenclature() {
		return nomenclature;
	}

	public long getRequired() {
		return required;
	}

	public long getAuthorized() {
		return authorized;
	}

	public long getOnhand() {
		return onhand;
	}

	public long getDuein() {
		return duein;
	}

	public long getShortage() {
		return shortage;
	}

	public String getRating() {
		return rating;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getDocNum() {
		return docNum;
	}

	@Override
	public int compareTo(Item o) {
		return lin.compareToIgnoreCase(o.lin);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nomenclature;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (authorized == null) {
			if (other.authorized != null) {
				return false;
			}
		} else if (!authorized.equals(other.authorized)) {
			return false;
		}
		if (docNum == null) {
			if (other.docNum != null) {
				return false;
			}
		} else if (!docNum.equals(other.docNum)) {
			return false;
		}
		if (duein == null) {
			if (other.duein != null) {
				return false;
			}
		} else if (!duein.equals(other.duein)) {
			return false;
		}
		if (lin == null) {
			if (other.lin != null) {
				return false;
			}
		} else if (!lin.equals(other.lin)) {
			return false;
		}
		if (nomenclature == null) {
			if (other.nomenclature != null) {
				return false;
			}
		} else if (!nomenclature.equals(other.nomenclature)) {
			return false;
		}
		if (onhand == null) {
			if (other.onhand != null) {
				return false;
			}
		} else if (!onhand.equals(other.onhand)) {
			return false;
		}
		if (rating == null) {
			if (other.rating != null) {
				return false;
			}
		} else if (!rating.equals(other.rating)) {
			return false;
		}
		if (remarks == null) {
			if (other.remarks != null) {
				return false;
			}
		} else if (!remarks.equals(other.remarks)) {
			return false;
		}
		if (required == null) {
			if (other.required != null) {
				return false;
			}
		} else if (!required.equals(other.required)) {
			return false;
		}
		if (shortage == null) {
			if (other.shortage != null) {
				return false;
			}
		} else if (!shortage.equals(other.shortage)) {
			return false;
		}
		if (sublin == null) {
			if (other.sublin != null) {
				return false;
			}
		} else if (!sublin.equals(other.sublin)) {
			return false;
		}
		return true;
	}

}

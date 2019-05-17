public class Words implements Comparable<Words> {
	String key;
	String value;

	@Override
	public int compareTo(Words o) {
		return key.compareTo(o.key);
	}

	@Override
	public boolean equals(Object obj) {
		return key.compareTo(((Words) obj).key) == 0;

	}

	@Override
	public String toString() {
		return "Word: " + key + " \n " + "Meaning: " + value;
	}
}

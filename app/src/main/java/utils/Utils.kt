package utils


class Utils {
    companion object {

        /**
         * encode email replace the dot with \u002e So that it can appear as a key in firebase
         * @param key - decode email
         * @return string - encode email
         */
        fun encodeKey(key: String?): String {
            return key!!.replace(".", "\\u002e").replace("\\", "\\\\").replace("\$", "\\u0024")
        }

        /**
         *  decode email replace the \u002e with dot
         * @param key - encode email
         * @return string - decode email
         */
        fun decodeKey(key: String?): String {
            return key!!.replace("\\u002e", ".").replace("\\u0024", "\$").replace("\\\\", "\\")
        }
    }
}
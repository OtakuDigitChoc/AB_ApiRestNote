import note.Note

class BootStrap {

    def init = { servletContext ->
      def note1 = new Note(content : 'ma premier note')

      def note2 = new Note(content : 'ma 2 note')
      def note3 = new Note(content : 'ma 3 note')
      def note4 = new Note(content : 'ma 4 note')
      note1.save(flush : true)
      note2.save(flush : true)
      note3.save(flush : true)
      note4.save(flush : true)
    }
    def destroy = {
    }
}

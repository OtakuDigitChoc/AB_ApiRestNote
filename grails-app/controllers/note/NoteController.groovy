package note


import grails.converters.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NoteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Note.list(params), model:[noteInstanceCount: Note.count()]
    }

    def show(Note noteInstance) {
        respond noteInstance
    }

    def create() {
        respond new Note(params)
    }

    def list() {
      render Note.list(params) as JSON
    }

    def create_note(params) {
      def newNote = new Note(content: params.content)
      newNote.save(flush : true)
      render newNote as JSON
    }

    def delete_note(params) {
      def message = [message : "erreur"]
      if (params.id != null){
        message.message = "ok"
        Note oldNote = Note.get(params.id)
        render message as JSON
        oldNote.delete(flush: true)
      }else {
        render message as JSON
      }
    }


    @Transactional
    def save(Note noteInstance) {
        if (noteInstance == null) {
            notFound()
            return
        }

        if (noteInstance.hasErrors()) {
            respond noteInstance.errors, view:'create'
            return
        }

        noteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'note.label', default: 'Note'), noteInstance.id])
                redirect noteInstance
            }
            '*' { respond noteInstance, [status: CREATED] }
        }
    }

    def edit(Note noteInstance) {
        respond noteInstance
    }

    @Transactional
    def update(Note noteInstance) {
        if (noteInstance == null) {
            notFound()
            return
        }

        if (noteInstance.hasErrors()) {
            respond noteInstance.errors, view:'edit'
            return
        }

        noteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Note.label', default: 'Note'), noteInstance.id])
                redirect noteInstance
            }
            '*'{ respond noteInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Note noteInstance) {

        if (noteInstance == null) {
            notFound()
            return
        }

        noteInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Note.label', default: 'Note'), noteInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'note.label', default: 'Note'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

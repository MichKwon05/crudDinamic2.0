<template>
  <b-container fluid>
    <b-row class="p-5 text-end">
      <b-col>
        <b-button variant="primary" @click="toggleForm">Agregar</b-button>
      </b-col>
    </b-row>

    <b-row v-if="showForm" class="animated fadeInRight">
      <b-col>
        <b-modal id="modal-1" :title="modalTitle" scrollable centered @ok="insertBook">
          <form ref="form">
            <div class="mb-3">
              <label for="author" class="form-label">Autor:</label>
              <input type="text" id="author" v-model="author" class="form-control">
            </div>
            <div class="mb-3">
              <label for="nameBook" class="form-label">Nombre del Libro:</label>
              <input type="text" id="nameBook" v-model="nameBook" class="form-control">
            </div>
            <div class="mb-3">
              <label for="gener" class="form-label">Género:</label>
              <input type="text" id="gener" v-model="gener" class="form-control">
            </div>
            <div class="mb-3">
              <label for="age" class="form-label">Año de Publicación:</label>
              <input type="number" id="age" v-model.number="age" class="form-control">
            </div>
          </form>
        </b-modal>
      </b-col>
    </b-row>

    <b-row>
      <b-col v-if="cargando" class="d-flex justify-content-center mb-3">
        <b-spinner style="width: 3rem; height: 3rem"></b-spinner>
      </b-col>
      <b-col v-else v-for="(libro, index) in books" :key="libro.id" class="entity-card">
        <b-card :title="libro.author" img-src="URL_DE_LA_IMAGEN" tag="article" style="max-width: 20rem" class="mb-2"
          @click="updateBooks(libro)">
          <b-card-text>
            {{ libro.nameBook }}
          </b-card-text>
          <b-card-text>
            {{ libro.gener }}
          </b-card-text>
          <b-card-text>
            {{ libro.age }}
          </b-card-text>
          <div class="text-end">
            <b-button variant="danger" @click="deleteBooks(libro.id)">Eliminar</b-button>
          </div>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import books from "../services/Books";
import draggable from "vuedraggable";
import "vue2-animate/dist/vue2-animate.min.css";

export default {
  data() {
    return {
      author: "",
      nameBook: "",
      gener: "",
      age: "",
      books: [],
      status: false,
      cargando: false,
      updateBook: {
        id: null,
        author: "",
        nameBook: "",
        gener: "",
        age: "",
      },
    };
  },
  mounted() {
    this.consultBooks();
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
  },
  methods: {
    async insertBook(event) {
      const currentYear = new Date().getFullYear();
      try {
        if (this.age > currentYear) {
          throw new Error("El año de edición no es correcto");
        }

        if (this.status) {
          await books.onUpdate(
            this.updateBook.id,
            this.author,
            this.nameBook,
            this.gener,
            this.age
          );
        } else {
          await books.onInsert(
            this.author,
            this.nameBook,
            this.gener,
            this.age
          );
        }
        this.consultBooks();
      } catch (error) {
        alert(error.message);
      } finally {
        this.$bvModal.hide("modal-1");
        this.resetForm();
      }
    },

    resetForm() {
      this.author = "";
      this.nameBook = "";
      this.gener = "";
      this.age = "";
      this.status = false;
    },

    async consultBooks() {
      try {
        this.cargando = true;
        const result = await books.onGetAll();
        this.books = result;
      } catch (error) {
        alert("Error al consultar los libros");
      } finally {
        this.cargando = false;
      }
    },

    async deleteBooks(libroId) {
      try {
        await books.onDelete(libroId);
        this.consultBooks();
      } catch (error) {
        alert("Error al eliminar el libro");
      }
    },

    updateBooks(libro) {
      this.updateBook = { ...libro };
      this.author = this.updateBook.author;
      this.nameBook = this.updateBook.nameBook;
      this.gener = this.updateBook.gener;
      this.age = this.updateBook.age;
      this.$bvModal.show("modal-1");
      this.status = true;
    },

    toggleForm() {
      this.showForm = !this.showForm;
    },

    handleScroll() {
      // Puedes ajustar el umbral según tus necesidades
      const threshold = 100;
      this.showForm = window.scrollY < threshold;
    },
  },
  components: {
    draggable,
  },
};
</script>

<style lang="scss">
.entity-card {
  transition: transform 0.5s ease-in-out;
}

.animated {
  animation-fill-mode: both;
}

.fadeInRight {
  animation-name: fadeInRight;
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translate3d(100%, 0, 0);
  }

  to {
    opacity: 1;
    transform: none;
  }
}
</style>

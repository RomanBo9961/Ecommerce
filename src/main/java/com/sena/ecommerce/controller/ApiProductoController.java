package com.sena.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IProductoService;
import com.sena.ecommerce.service.IUsuarioService;
import com.sena.ecommerce.service.UploadFileService;

@RestController
@RequestMapping("/apiproductos")
public class ApiProductoController {

	@Autowired
	private IProductoService productoservice;

	@Autowired
	private UploadFileService upload;

	@Autowired
	private IUsuarioService usuarioservice;

	// endpoint GET para obtener todos los productos
	@GetMapping("/api")
	public List<Producto> getAllProducts() {
		return productoservice.findAll();
	}

	// endpoint GET para obtener un producto por ID
	@GetMapping("/api/{id}")
	public ResponseEntity<Producto> getProductById(@PathVariable Integer id) {
		Optional<Producto> producto = productoservice.get(id);
		return producto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// endpoint POST para crear un producto
	@PostMapping("/api")
	public ResponseEntity<Producto> createProduct(@RequestBody Producto producto) {
		Usuario u = usuarioservice.findById(1).get();
		producto.setUsuario(u);
		if (producto.getImagen() == null)
			producto.setImagen("default.jpg");
		Producto sp = productoservice.save(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(sp);
	}

	// endpoint PUT para actualizar producto
	@PutMapping("/api/{id}")
	public ResponseEntity<Producto> updateProduct(@PathVariable Integer id, @RequestBody Producto producto) {
		Optional<Producto> p = productoservice.get(id);
		if (!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Producto ep = p.get();
		ep.setNombre(producto.getNombre());
		ep.setDescripcion(producto.getDescripcion());
		ep.setPrecio(producto.getPrecio());
		ep.setCantidad(producto.getCantidad());

		if (producto.getImagen() != null) {
			ep.setImagen(producto.getImagen());
		}

		productoservice.update(ep);

		return ResponseEntity.ok(ep);
	}

	// endpoint DELETE para ELIMINAR producto
	@DeleteMapping("/api/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		Optional<Producto> p = productoservice.get(id);
		if (!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Producto prod = p.get();
		if (!prod.getImagen().equals("default.jpg")) {
			upload.deleteImage(prod.getImagen());
		}
		productoservice.delete(id);
		return ResponseEntity.ok().build();
	}

}
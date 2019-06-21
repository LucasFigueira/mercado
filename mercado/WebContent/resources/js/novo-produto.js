var inicio = new Vue({
	el:"#app",  
    	 data() {
    	      return {
    	    	  visivel: true , 
    	    	  produto:{ 
    	     		 id: '',
    	     		nome: '', 		
    	     	 	fabricante:{
    	     	 		nome: ''
    	     	 	},  	 	
    	     	 	volume: '',	 	
    	     	 	unidade: '',  	 	
    	     	 	estoque: '',  	 	
    	     	 },
    	     	 nextId: 0,
    	     	showModal: false,
    	        listaProdutos: [],
    	        listaProdutosHeader: [
    				{sortable: false, key: "nome", label:"Nome"},
    				{sortable: false, key: "fabricante.nome", label:"Fabricante"},
    				{sortable: false, key: "volume", label:"Volume"},
    				{sortable: false, key: "unidade", label:"Unidade"},
    				{sortable: false, key: "estoque", label:"Estoque"}
    			]
    	    } 
    },
    created: function(){
        let vm =  this;
        vm.buscaProdutos(); 
    },
    methods:{
        buscaProdutos: function(){
			const vm = this;
			axios.get("/mercado/rs/produtos")
			.then(response => {
				vm.listaProdutos = response.data;
			}).catch(function (error) {
				vm.mostraAlertaErro("Erro interno", "Não foi listar natureza de serviços");
			}).finally(function() {
			});
		},
		
		adicionarProduto(produto){ 
			 let vm =  this;
	    			console.log(JSON.stringify(produto))
	    			axios({
	                    method: 'POST',
	                    url: "/mercado/rs/produtos", 
	                    data: JSON.stringify(produto), 
	                    headers:{'Content-Type': 'application/json; charset=utf-8'}
	                }) 
	          .then(response => {
			   console.log(response); 
			}).catch(function (error) {
				console.log(error)
			}).finally(function() { 
	        	vm.visivel = true 
		         produto.id = ''
		         produto.nome = ''
		        produto.fabricante.nome = '',
		        produto.volume = '',
		        produto.unidade = '',
		        produto.estoque = '',
	        	vm.buscaProdutos();
	        	
			}); 
		},
 
	    test(produto){
	    	console.log(produto)
	    	console.log(this.produto)
	    }
    }
})

 
 

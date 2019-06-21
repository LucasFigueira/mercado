var inicio = new Vue({
	el:"#inicio",  
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
    	     	 post: Object,
    	     	showModal: false,
    	        listaProdutos: [],
    	        listaAtt: [],
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
				console.log(vm.listaProdutos)
			}); 
		},
		
		deletaProduto:  function(id){
			const vm = this;
			axios.delete("/mercado/rs/produtos/"+id)
			.then(response => {
				vm.response = response.data;
			}).catch(function (error) {
				vm.mostraAlertaErro("Erro interno", "Não foi listar natureza de serviços");
			}).finally(function() {
				vm.buscaProdutos();
			});
        },
        
        editarProduto: function(nome,index){
        	const vm = this; 
        	 if(vm.visivel){
        		 vm.visivel = false
        	 }else{
        		 vm.visivel = true
        	 }
        }, 
        
        alterarProduto(produto){ 
        	const vm = this; 
        	axios({
                method: 'PUT',
                url: "/mercado/rs/produtos/"+produto.id, 
                data: JSON.stringify(produto), 
                headers:{'Content-Type': 'application/json; charset=utf-8'}
            })
        	.then(response => {
				console.log(response)
			}).catch(function (error) {
				console.log(error)
			}).finally(function() {
				vm.buscaProdutos();
	        	vm.visivel = true
			}); 
	    },
	    
	    test(produto){
	    	console.log(produto)
	    	console.log(this.produto)
	    }
    }
})

 
 


ingrediente('relleno de empanada', 'huevo', 2).
ingrediente('relleno de empanada', 'carne', 1).
ingrediente('relleno de empanada', 'sal', 1).
ingrediente('relleno de empanada', 'aceite', 1).
ingrediente('masa', 'levadura', 1).
ingrediente('masa', 'harina', 3).
ingrediente('masa', 'aceite', 1).
ingrediente('empanada de carne', 'masa', 1).
ingrediente('empanada de carne', 'relleno de empanada', 1).
ingrediente('empanada de carne', 'aceite', 1).
ingrediente('salsa de tomate', 'tomate', 3).
ingrediente('salsa de tomate', 'aceite', 1).
ingrediente('queso', 'leche', 2).
ingrediente('queso', 'sal', 1).
ingrediente('pizza muzzarella', 'masa', 1).
ingrediente('pizza muzzarella', 'queso', 2).
ingrediente('pizza muzzarella', 'salsa de tomate', 1).
ingrediente('pizza muzzarella', 'sal', 1).
ingrediente('bizcochuelo', 'huevo', 3).
ingrediente('bizcochuelo', 'azúcar', 2).
ingrediente('bizcochuelo', 'harina', 2).
ingrediente('bizcochuelo', 'leche', 1).
ingrediente('bizcochuelo', 'aceite', 1).
ingrediente('caramelo', 'azúcar', 2).
ingrediente('caramelo', 'leche', 1).
ingrediente('huevo frito', 'huevo', 1).
ingrediente('huevo frito', 'aceite', 1).
ingrediente('hamburguesa', 'masa', 2).
ingrediente('hamburguesa', 'queso', 1).
ingrediente('hamburguesa', 'carne', 1).
ingrediente('hamburguesa', 'sal', 1).
ingrediente('hamburguesa', 'huevo frito', 1).
ingrediente('hamburguesa', 'tomate', 1).
ingrediente('matambre a la pizza', 'queso', 2).
ingrediente('matambre a la pizza', 'salsa de tomate', 2).
ingrediente('matambre a la pizza', 'carne', 1).
ingrediente('dulce de leche', 'azúcar', 2).
ingrediente('dulce de leche', 'leche', 1).
ingrediente('merengue', 'huevo', 3).
ingrediente('merengue', 'azúcar', 2).
ingrediente('torta', 'merengue', 1).
ingrediente('torta', 'bizcochuelo', 1).
ingrediente('torta', 'dulce de leche', 1).
ingrediente('papas fritas', 'papa', 3).
ingrediente('papas fritas', 'aceite', 2).
ingrediente('matambre a la pizza con fritas', 'papas fritas', 1).
ingrediente('matambre a la pizza con fritas', 'matambre a la pizza', 1).
ingrediente('asado', 'carne', 1).
ingrediente('asado', 'sal', 1).
ingrediente('flan', 'huevo', 4).
ingrediente('flan', 'azúcar', 1).
ingrediente('flan', 'leche', 2).
ingrediente('flan con caramelo', 'caramelo', 1).
ingrediente('flan con caramelo', 'flan', 1).

% Para verificar si tengo suficientes unidades de un ingrediente básico
suficiente(Ingrediente, CantidadNecesaria) :-
    tengo(Ingrediente, CantidadTengo),
    CantidadTengo >= CantidadNecesaria.

% Verifica si todos los ingredientes de un producto están disponibles
puedo_hacer(Producto) :-
    ingrediente(Producto, _, _),  % El producto existe
    forall( 
        ingrediente(Producto, Ingrediente, CantidadNecesaria),
        suficiente(Ingrediente, CantidadNecesaria)
    ).

% Armamos una lista, solo se guarda 1 vez sin repetidos
productos_posibles(Lista) :-
    setof(Producto, puedo_hacer(Producto), Lista).

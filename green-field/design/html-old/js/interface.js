(function($) 
{
	/* Plugin p/ placeholder de inputs de formulário. */
	jQuery.fn.extend({
		placeholder: function(value) 
		{
			$(this).val(value);
			$(this).focus(function() 
			{
				if( $(this).val() === value ) 
				{
					$(this).val(''); 
				}
			});
			$(this).blur(function() 
			{
				if( $(this).val() === '' ) 
				{
					$(this).val(value); 
				}
			});
			return this;
		}
	});
	
	$(document).ready(function() 
	{
		$('#form-search-input').placeholder('Procurar pessoas e/ou postagens...');
	
	
	});
	
})(jQuery);
(function() {
  var Document;
  Document = ORBEON.xforms.Document;
  $(function() {
    return $(document).on('change.orbeon', '#dialog-itemsets' + XF_COMPONENT_SEPARATOR + 'dialog .fb-itemset-label-input', function(event) {
      var label, newValue, value;
      label = $(event.target);
      value = label.closest('tr').find('.fb-itemset-value-input')[0];
      if ($.trim(Document.getValue(value)) === '') {
        newValue = $.trim(label.val());
        newValue = newValue.replace(new RegExp(' ', 'g'), '-');
        newValue = newValue.toLowerCase();
        return Document.setValue(value, newValue);
      }
    });
  });
}).call(this);

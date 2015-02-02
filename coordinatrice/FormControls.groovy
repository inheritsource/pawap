/*
 * How to extract section and control names from an Orbeon form definition
 */
DIR = new File('/home/hs/Downloads')
//FILE = 'serveringstillstand_ansokan--v018_form.xhtml'
//FILE = 'test_allwidgets--v003_06_form.xml'
//FILE = 'OFV_OFVgeInfo--v002_form.xhtml'
FILE = 'OFV_OFVStartForm--v008_form.xhtml'
def input = new File(DIR, FILE)
def xml = new XmlSlurper().parse(input)
def instance = xml.'**'.find {it.@id == 'fr-form-instance'}
def sections = []
instance.form.'*'.each {section ->
  def descr = [section: section.name()]
  def controls = []
  section.'*'.each {control ->
    controls << control.name()
  }
  descr.controls = controls
  sections << descr
}
println sections
